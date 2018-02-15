package airport;
import java.util.HashSet;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class FlightList {
	
	private static final String FILENAME = "FlightReport.txt";
	private HashSet<Flight> flights;
	
	public FlightList() {
		flights = new HashSet<Flight>();
	}
	
	protected Flight findByCode(String code) throws NoMatchingFlightCodeException{
		for (Flight f : flights) {
			if(f.getFlightCode().equals(code)) {
				System.out.println("Flight found");
				return f;
			}
		}
		throw new NoMatchingFlightCodeException(code);		
	}
	
	protected int getNumberOfEntries() {
		return flights.size();
	}
	
	protected void add(Flight flight) {
		flights.add(flight);
	}
	
//	public void printFlightList(){
//		for (Flight f : flights) {
//			System.out.println("Flight: " + f.getFlightCode()); 
//			System.out.println("Baggage fees: " + f.getTotalBaggageFees());
//			System.out.println("Total weight: " + f.getTotalWeight());
//			System.out.println("Total volume: " + f.getTotalVolume());
//			System.out.println("Total passengers: " + f.getTotalPassengers());
//			if (f.getMaxPassengers()<f.getTotalPassengers()) {
//				System.out.println("Flight capacity exceeded by: " 
//						+ (f.getTotalPassengers() - f.getMaxPassengers()));
//			}
//			else if (f.getMaxPassengers()>f.getTotalPassengers()) {
//				System.out.println("Vacant seats: " + (f.getMaxPassengers()-f.getTotalPassengers()));
//			}
//			else if (f.getMaxPassengers()==f.getTotalPassengers()) {
//				System.out.println("Flight full");
//			}
//			System.out.println();
//		}
//	}
//	
	
	protected void printFlightList() throws IOException {
		System.out.println("I am being called");
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		String capacity = null;
		try {	
				fw = new FileWriter(FILENAME);
				bw = new BufferedWriter(fw);
				for (Flight f : flights) {
					String content = "Flight: " + f.getFlightCode();
					String fees = "Baggage fees: " + f.getTotalBaggageFees();
					String weight = "Total weight: " + f.getTotalWeight();
					String volume = "Total volume: " + f.getTotalVolume();
					String passengers = "Total passengers: " + f.getTotalPassengers();
					if (f.getMaxPassengers()<f.getTotalPassengers()) {
						capacity = "Flight capacity exceeded by: " 
								+ (f.getTotalPassengers() - f.getMaxPassengers());
					}
					else if (f.getMaxPassengers()>f.getTotalPassengers()) {
						capacity = "Vacant seats: " + (f.getMaxPassengers()-f.getTotalPassengers());
					}
					else if (f.getMaxPassengers()==f.getTotalPassengers()) {
						capacity = "Flight full";
					}
					bw.write(content + "\n");
					bw.write(fees + "\n");
					bw.write(weight + "\n");
					bw.write(volume + "\n");
					bw.write(passengers + "\n");
					bw.write(capacity + "\n");
					bw.write("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bw!=null)
					bw.close();
				if(fw!=null)
						fw.close();
			}
			catch(IOException ex) {
				ex.printStackTrace();
				}
			}
		}
		
	// Just for testing
//	public static void main(String[] args) throws IOException {
//		FlightList flights1 = new FlightList();
//		Flight f1 = new Flight("L123G", "Illinois", "Lufthansa", 23, 5, 70);
//		Flight f2 = new Flight("L124G", "Kentucky", "Lufthansa", 23, 6, 70);
//		Flight f3 = new Flight("L125G", "Chicago", "Lufthansa", 23, 2, 70);
//		f2.incrementBaggageFees((float) 34.634);
//		f1.incrementPassengers();
//		f2.incrementPassengers();
//		f3.incrementPassengers();
//		f3.incrementPassengers();
//		f3.incrementPassengers();
//		f3.incrementBaggageFees((float) 34.634);
//		f3.incrementVolume((float) 99.56);
//		f3.incrementVolume((float) 31.44);
//		f3.incrementWeight((float) 128.56);
//		flights1.add(f1);
//		flights1.add(f2);
//		flights1.add(f3);
//		//flights1.findByCode("L134G");
//		flights1.getNumberOfEntries();
//		flights1.printFlightList();
//	}
}
