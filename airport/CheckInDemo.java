package airport;
import java.io.*;

import java.util.*;
import java.util.logging.Level;
import java.util.regex.Pattern;

import model.CheckIn;
import views.CheckInGUI;

public class CheckInDemo {
	
	private static PassengerList passengers;
	private static FlightList flights;
	private static CheckInGUI gui;
	private static CheckIn checkin;
	static int passengers_checked_in = 0;
	static int passengers_total = 0;
	//static CheckIn checkin;

	//Single passenger queue thread
	private static Thread passenger_queue;
	//Threads to store what desks are open and what flights have not departed
	static ArrayList<Thread> active_flights;
	static ArrayList<Thread> check_in_desks;
	
	//List of what flights have not yet departed (used for checking if a user can check in)
	static ArrayList<Flight> flights_left_to_depart;

	public CheckInDemo() throws IOException, InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
		
		passengers = new PassengerList();
		flights = new FlightList();
		BufferedReader buff1 = null;
		BufferedReader buff2 = null;
		String data1 [] = new String[4];
		String data2 [] = new String[5];
		try {
			// Parse flightList and passengerList text files, storing data in flightList and passengerList data structures
			buff1 = new BufferedReader(new FileReader("passengerTest.txt"));
			buff2 = new BufferedReader(new FileReader("flightTest.txt"));
			String inputLine1 = buff1.readLine();
			String inputLine2 = buff2.readLine();
			while(inputLine1 != null) {
				data1 = inputLine1.split(";");
				Passenger p = new Passenger(data1[0], data1[1], data1[2], data1[3], data1[4]);
				passengers.addPassenger(p);
				inputLine1 = buff1.readLine();
			}
			while(inputLine2 != null) {
				data2 = inputLine2.split(";");
				Flight f = new Flight(data2[0], data2[1], data2[2], Float.parseFloat(data2[3]), Integer.parseInt(data2[4]), Float.parseFloat(data2[5]));
				boolean flight_code_verified = check_flight_code(data2[0]);
				if(flight_code_verified == true) {
					flights.add(f);
				}
				inputLine2 = buff2.readLine();
			}

			passengers_total = passengers.getPassengersNotCheckedIn();
			Iterator<Passenger> it = passengers.getIterator();
			while(it.hasNext()) {
				Passenger passenger = it.next();
				try {
					Flight flight = flights.findByCode(passenger.getFlightCode());
					passenger.setBaggageFee(flight.calculateExcessBaggageFees(passenger.getBaggageWeight()));
				} catch (NoMatchingFlightCodeException e) {
					e.printStackTrace();
				}
			}
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		catch(InvalidParameterException e) {
			System.out.println(e.getMessage());
		}
		catch(InvalidFlightCodeException e) {
			System.out.println(e.getMessage());
		}
		catch(InvalidBookingRefException e) {
			System.out.println(e.getMessage());
		}
		finally {
			try {
				buff1.close();
				buff2.close();
			}
			catch (IOException ioe){
				
			}
		}
	}
	
	//Function called when a Flight thread is ready to depart. Closes all check in desks if all flights have departed
	protected static synchronized void flight_depart(Thread thread, String flight_code) { //This will get used when we've implemented the flights leaving bit
		
		//Make a new list from flights_left_to_depart to prevent ConurrentModificationException caused by it.next();
		Flight[] list_iterator = flights_left_to_depart.toArray(new Flight[flights_left_to_depart.size()]);
		for(int i=0; i<list_iterator.length; i++) {
			Flight flight_departing = list_iterator[i];
			if(flight_departing.getFlightCode().equals(flight_code)) {
				flights_left_to_depart.remove(flight_departing);
			}
		}
		
		//System.out.println("Flight code being removed = "+flight_code);
		active_flights.remove(thread);
		gui.update_flight(flight_code, "Departed");
		
		//If there are no flights left to depart, wait a bit and then close the kiosk
		if(active_flights.isEmpty()) {
			System.out.println("ACTIVE FLIGHTS IS EMPTY");
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Kiosk Closing");
			passenger_queue.interrupt();
			System.exit(0);
		}
	}
	
	protected static void check_in_passenger(Passenger passenger) {
		passengers_checked_in++;
		
		if(passengers_checked_in == passengers_total) {
			try {
				flights.printFlightList();
			} catch (IOException e) {
				System.out.println("Cannot print");
			}
			System.exit(0);
		}
	}
	
	// Checks flight code format
	public synchronized boolean check_flight_code(String flight_code) {
		Pattern q = Pattern.compile("^[a-zA-Z]{2}[0-9]{4}");
		boolean valid = q.matcher(flight_code).find();
		if(valid == true && !flight_code.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//Checks the flights_left_to_depart list and returns whether the flight has departed or not
	//Used for determining whether a passenger is allowed to check in to their respective flights
	protected synchronized static boolean has_flight_departed(Passenger passenger) {
		String flight_code_of_passenger = passenger.getFlightCode();
		Iterator<Flight> it = flights_left_to_depart.iterator();
		while(it.hasNext()) {
			if(it.next().getFlightCode().equals(flight_code_of_passenger)) {
				return false;
			}
		}
		return true;
	}
	
	//Called whenever the passenger queue changes, opens or closes check in desks depending on how long the queue is
	protected synchronized static void open_close_check_in_desks(int size_of_queue) {
		if(size_of_queue > 2) {
			int index = check_in_desks.size();
			//Add another check in desk while size is less than 5
			while(check_in_desks.size() < 5) {
				check_in_desks.add(new Thread(new CheckInDesk(PassengerQueue.get_passenger_queue(), flights, gui, index)));
				check_in_desks.get(index).start();
				gui.update_checkInDesk(index, "OPEN");
				index++;
			}
		}
		else if(size_of_queue < 2) {
			//Remove desks while size is more than 1
			int index = check_in_desks.size();
			while(check_in_desks.size() > 1) {
				//check_in_desks.get(0).interrupt();
				check_in_desks.remove(0);
				gui.update_checkInDesk(index, "CLOSED");
				index--;
			}
		}
	}
	
	//Used for creating a string used in the GUI about flight details, this method might get moved
	protected static synchronized String get_current_flight_capacity_info(Flight current_flight) {
		String capacity_info = "";
		//THIS UPDATES GUI - THIS SHOULD NOT HAPPEN HERE!!
		capacity_info = current_flight.getTotalPassengers()+" checked in of "+current_flight.getMaxPassengers()+"\nHold is "+check_hold_fill_percentage(current_flight.getMaxVol(), current_flight.getTotalVolume())+"% full\nBaggage Fees = "+current_flight.getTotalBaggageFees();
		gui.update_flight(current_flight.getFlightCode()+" "+current_flight.getDestination(), capacity_info);
		
		return capacity_info;
	}
	
	//Check (percentage-wise) how full the plane is
	private static int check_hold_fill_percentage(float maximum_volume, float current_volume) {
		int percentage = 0;
		
		percentage = (int)((current_volume/maximum_volume)*100);
		
		return percentage;		
	}
		
	public static void main(String args[]) throws IOException, InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
		CheckInDemo demo = new CheckInDemo();
		CheckIn check = new CheckIn();
		
		//List storing all the flights which have not yet departed
		//With runnable, you can't access methods of the object a thread refers to (I think)
		//So I need a separate list containing a list of the flights that haven't departed so I can check their
		//flight codes when a passenger tries to check in
		flights_left_to_depart = new ArrayList<Flight>();
		
		try {
			gui = new CheckInGUI(check, PassengerQueue.get_passenger_queue());
		} catch (InvalidFlightCodeException | InvalidBookingRefException | InvalidParameterException e) {
			System.out.println("Invalid Parameters");
		}
		
		//Add a passenger queue and start reading data from the file
		//for(int i=0; i<3; i++) {
		//LOG: Check In Desks Opening
		//String desk_open = "Check In Desk " + i + " opening";
			//AirportLog.log(Level.INFO,desk_open);
		//}
		
		passenger_queue = new Thread(new PassengerQueue(gui, passengers, check));
		passenger_queue.start();
		
		//Initially open 3 check in desks, this gets changed throughout the program though
		check_in_desks = new ArrayList<Thread>();
		for(int i=0; i<3; i++) {
			check_in_desks.add(new Thread(new CheckInDesk(PassengerQueue.get_passenger_queue(), flights, gui, i)));
			check_in_desks.get(i).start();
			//LOG: Check In Desks Opening
			//String desk_open = "Check In Desk " + i + " opening";
			//AirportLog.log(Level.INFO,desk_open);
		}
		
		active_flights = new ArrayList<Thread>();
		Iterator<Flight> it = flights.get_iterator();
		while(it.hasNext()) {
			Flight temp_flight = it.next();
			//Add a thread containing Flight to active_flights array list
			active_flights.add(new Thread(new Flight(temp_flight.getFlightCode(), temp_flight.getDestination(), temp_flight.getCarrier(), temp_flight.getMaxWeight(), temp_flight.getMaxPassengers(), temp_flight.getMaxVol())));
			//Add this to the flights left to depart arraylist
			flights_left_to_depart.add(temp_flight);
			String flight_info = temp_flight.getFlightCode()+" "+temp_flight.getDestination();
			String flight_status = temp_flight.getTotalPassengers()+" checked in of "+temp_flight.getMaxPassengers()+"\nHold is "+check_hold_fill_percentage(temp_flight.getMaxVol(), temp_flight.getTotalVolume())+"% full";
			//Update the GUI with the relevant flights
			gui.update_flight(flight_info, flight_status);
		}
		
		//Start the threads
		for(int i=0; i<active_flights.size(); i++) {
			active_flights.get(i).start();
		}
		
		//This is a GUI which allows passengers to add themselves to the GUI (alternate way of adding passengers to passenger_queue)
		PassengerEntryGUI passenger_entry_gui = new PassengerEntryGUI(passengers, flights);
	}
}
