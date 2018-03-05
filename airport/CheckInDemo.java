package airport;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.regex.Pattern;


public class CheckInDemo {
	private static PassengerList passengers;
	private static FlightList flights;
	private static KioskGUI gui;
	static int passengers_checked_in = 0;
	static int passengers_total = 0;
	
	static ArrayList<Passenger> passenger_queue;

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
			Collections.shuffle(passenger_queue);
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
	
	private void showGUI() {
		gui = new KioskGUI();
		gui.start_gui(passengers, flights);
		PassengerEntryGUI passenger_entry_gui = new PassengerEntryGUI(passengers, flights, passenger_queue);
	}

	protected static void check_in_passenger(Passenger passenger) {
		passengers_checked_in++;
		if(passengers_checked_in == passengers_total) {
			gui.close_gui();
			try {
				flights.printFlightList();
			} catch (IOException io_exception) {
				System.out.println("Cannot print");
			}
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
		CheckInDemo demo = new CheckInDemo();
		//demo.showGUI();
		Thread check_in_desk1 = new Thread(new CheckInDesk(passenger_queue));
		check_in_desk1.start();
		Thread check_in_desk2 = new Thread(new CheckInDesk(passenger_queue));
		check_in_desk2.start();
		
		while(true) {
			
		}
	}
}
