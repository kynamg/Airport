package airport;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.regex.Pattern;


public class CheckInDemo {
	private static PassengerList passengers;
	private static FlightList flights;
	private static KioskGUI gui;
	static int passengers_checked_in = 0;
	static int passengers_total = 0;

	public CheckInDemo() throws IOException, InvalidFlightCodeException, InvalidBookingRefException {
		passengers = new PassengerList();
		flights = new FlightList();
		BufferedReader buff1 = null;
		BufferedReader buff2 = null;
		String data1 [] = new String[3];
		String data2 [] = new String[5];
		try {
			buff1 = new BufferedReader(new FileReader("passengerTest.txt"));
			buff2 = new BufferedReader(new FileReader("flightTest.txt"));
			String inputLine1 = buff1.readLine();
			String inputLine2 = buff2.readLine();
			while(inputLine1 != null) {
				data1 = inputLine1.split(";");
				boolean checkedIn = false;
				Passenger p = new Passenger(data1[0], data1[1], data1[2], data1[3], checkedIn);
				passengers.addPassenger(p);
				//System.out.println(p.getBookingRef());
				inputLine1 = buff1.readLine();
			}
			while(inputLine2 != null) {
				data2 = inputLine2.split(";");
				Flight f = new Flight(data2[0], data2[1], data2[2], Float.parseFloat(data2[3]), Integer.parseInt(data2[4]), Float.parseFloat(data2[5]));
				boolean flight_code_verified = check_flight_code(data2[0]);
				if(flight_code_verified == true) {
					flights.add(f);
				}
				//System.out.println(f.getFlightCode());
				inputLine2 = buff2.readLine();
			}

			passengers_total = passengers.getSizeOfList();
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
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
		gui = new KioskGUI(passengers, flights);
	}
	
	protected static void check_in_passenger() {
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
	
	public static void main(String arg[]) throws IOException, InvalidFlightCodeException, InvalidBookingRefException {
		CheckInDemo demo = new CheckInDemo();
		demo.showGUI();	
	}
}
