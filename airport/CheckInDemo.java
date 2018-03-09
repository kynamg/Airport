package airport;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Pattern;

import views.CheckInGUI;

public class CheckInDemo {
	private static PassengerList passengers;
	private static FlightList flights;
	private static CheckInGUI gui;
	private static Thread passenger_queue;
	static int passengers_checked_in = 0;
	static int passengers_total = 0;

	static ArrayList<Thread> active_flights;
	static ArrayList<Thread> check_in_desks;
	
	static ArrayList<String> flights_left_to_depart;

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
	
	protected static void flight_depart(Thread thread, String flight_code) { //This will get used when we've implemented the flights leaving bit
		flights_left_to_depart.remove(flight_code);
		System.out.println("Flight code being removed = "+flight_code);
		active_flights.remove(thread);
		gui.update_flight(flight_code, "Departed");
		if(active_flights.isEmpty()) {
			System.out.println("ACTIVE FLIGHTS IS EMPTY");
			for(int i=0; i<check_in_desks.size(); i++) {
				check_in_desks.get(i).interrupt();
			}
			System.out.println("Check In Desks closed");
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
	public boolean check_flight_code(String flight_code) {
		Pattern q = Pattern.compile("^[a-zA-Z]{2}[0-9]{4}");
		boolean valid = q.matcher(flight_code).find();
		if(valid == true && !flight_code.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	protected static boolean has_flight_departed(Passenger passenger) {
		String flight_code_of_passenger = passenger.getFlightCode();
		Iterator<String> it = flights_left_to_depart.iterator();
		while(it.hasNext()) {
			if(it.next().equals(flight_code_of_passenger)) {
				return false;
			}
		}
		return true;
	}
	
	protected static void open_close_check_in_desks(int size_of_queue) {
		if(size_of_queue > 3) {
			int index = check_in_desks.size();
			while(check_in_desks.size() < 4) {
				index++;
				check_in_desks.add(new Thread(new CheckInDesk(PassengerQueue.get_passenger_queue(), flights, gui, index)));
			}
		}
		else if(size_of_queue < 3) {
			while(check_in_desks.size() > 1) {
				check_in_desks.remove(0);
			}
		}
	}
		
	public static void main(String args[]) throws IOException, InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
		CheckInDemo demo = new CheckInDemo();
		
		flights_left_to_depart = new ArrayList<String>();
		
		try {
			gui = new CheckInGUI(PassengerQueue.get_passenger_queue());
		} catch (InvalidFlightCodeException | InvalidBookingRefException | InvalidParameterException e) {
			System.out.println("Invalid Parameters");
		}
		
		passenger_queue = new Thread(new PassengerQueue(gui, flights, passengers));
		passenger_queue.start();
		
		check_in_desks = new ArrayList<Thread>();
		for(int i=0; i<3; i++) {
			check_in_desks.add(new Thread(new CheckInDesk(PassengerQueue.get_passenger_queue(), flights, gui, i)));
			check_in_desks.get(i).start();
		}
		
		active_flights = new ArrayList<Thread>();
		Iterator<Flight> it = flights.get_iterator();
		while(it.hasNext()) {
			Flight temp_flight = it.next();
			active_flights.add(new Thread(new Flight(temp_flight.getFlightCode(), temp_flight.getDestination(), temp_flight.getCarrier(), temp_flight.getMaxWeight(), temp_flight.getMaxPassengers(), temp_flight.getMaxVol())));
			flights_left_to_depart.add(temp_flight.getFlightCode());
			gui.update_flight(temp_flight.getFlightCode(), "Waiting to depart");
		}
		for(int i=0; i<active_flights.size(); i++) {
			active_flights.get(i).start();
		}
		
		PassengerEntryGUI passenger_entry_gui = new PassengerEntryGUI(passengers, flights);
	}
}
