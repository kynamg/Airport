import java.io.*;


public class CheckInDemo {
	private static PassengerList passengers;
	private static FlightList flights;
	private KioskGUI gui;

	public CheckInDemo() throws IOException {
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
				flights.add(f);
				//System.out.println(f.getFlightCode());
				inputLine2 = buff2.readLine();
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
				buff1.close();
				buff2.close();
			}
			catch (IOException ioe){
				
			}
		}
		
		}
	
	public void showGUI() {
		KioskGUI gui = new KioskGUI(passengers, flights);
		//gui.run_gui(passengers, flights);
	}
	
	public static void main(String arg[]) throws IOException {
		CheckInDemo demo = new CheckInDemo();
		demo.showGUI();	
	}
}
