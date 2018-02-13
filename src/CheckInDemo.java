import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class CheckInDemo {
	private static PassengerList passengers;
	private static FlightList flights;
	private KioskGUI gui;
	static int passengers_checked_in = 0;

	public CheckInDemo() throws IOException {
		passengers = new PassengerList();
		BufferedReader buff = null;
		String data [] = new String[3];
		try {
			buff = new BufferedReader(new FileReader("passengerTest.txt"));
			String inputLine = buff.readLine();
			while(inputLine != null) {
				data = inputLine.split(";");
				boolean checkedIn = false;
				Passenger p = new Passenger(data[0], data[1], data[2], data[3], checkedIn);
				passengers.addPassenger(p);
				//System.out.println(p.getBookingRef());
				inputLine = buff.readLine();
				
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
		finally {
			try {
				buff.close();
			}
			catch (IOException ioe){
				
			}
		}
	}
	
	public void showGUI() {
		KioskGUI gui = new KioskGUI(passengers, flights);
		//gui.close_gui();
	}
	
	public static void check_in_passenger() {
		passengers_checked_in++;
		System.out.println("I have checked in a passenger "+passengers_checked_in);
	}
	
	public static void main(String arg[]) throws IOException {
		CheckInDemo demo = new CheckInDemo();
		demo.showGUI();	

	}
}
