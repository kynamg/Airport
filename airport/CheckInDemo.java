package airport;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.regex.Pattern;


public class CheckInDemo {
	private static PassengerList passengers;
	private static FlightList flights;
	private static KioskGUI gui;
	static int passengers_checked_in = 0;
	static int passengers_total = 0;
	
	static ArrayList<Passenger> passenger_queue;
	static ArrayList<Thread> check_in_desks; 

	public CheckInDemo() throws IOException, InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
		passenger_queue = new ArrayList<Passenger>();
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
				set_baggage(p);
				inputLine1 = buff1.readLine();
				
				if(p.getCheckIn() == false) {
					passenger_queue.add(p);
				}
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
			//Randomise order of passenger queue
			//This might not be needed if we're not randomly adding passengers - we don't have to
			//Collections.shuffle(passenger_queue);
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
	
	private static void set_baggage(Passenger passenger) {
		Random random = new Random();
		int baggage_weight = random.nextInt(50);
		int baggage_volume = random.nextInt(160);
		
		passenger.setBaggageWeight(baggage_weight);
		passenger.setBaggageVolume(baggage_volume);
	}
	
	private void showGUI() {
		gui = new KioskGUI();
		gui.start_gui(passengers, flights);
	}
	
	protected void flight_depart() { //This will get used when we've implemented the flights leaving bit
		for(int i=0; i<check_in_desks.size(); i++) {
			check_in_desks.get(i).interrupt();
		}
	}
	
	protected static void add_passenger_to_queue(Passenger passenger, boolean baggage_has_been_set) {
		
		if(baggage_has_been_set == false) {
			set_baggage(passenger);
		}
		
		passenger_queue.add(passenger);
		
		if(passenger_queue.size()>5) {
			while(check_in_desks.size()<3) {
				check_in_desks.add(new Thread(new CheckInDesk(passenger_queue, flights)));
			}
		}
		else if(passenger_queue.size()<2) {
			while(check_in_desks.size()>1) {
				check_in_desks.remove(0);
			}
		}
		
		System.out.println("Number of threads = "+check_in_desks.size());
		System.out.println("Passenger queue = "+passenger_queue.size());
	}

	protected static void check_in_passenger(Passenger passenger) {
		passengers_checked_in++;
		
		if(passengers_checked_in == passengers_total) {
			gui.close_gui();
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
	
	public static void main(String arg[]) throws IOException, InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
		//Still to do: while still alive thread
		passenger_queue = new ArrayList<Passenger>();
		CheckInDemo demo = new CheckInDemo();
		check_in_desks = new ArrayList<Thread>();
		//PassengerEntryGUI passenger_entry_gui = new PassengerEntryGUI(passengers, flights);
		for(int i=0; i<4; i++) {
			check_in_desks.add(new Thread(new CheckInDesk(passenger_queue, flights)));
			check_in_desks.get(i).start();
		}
	}
}
