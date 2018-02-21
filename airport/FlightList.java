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
	
	public Flight findByCode(String code) throws NoMatchingFlightCodeException{
		for (Flight f : flights) {
			if(f.getFlightCode().equals(code)) {
				return f;
			}
		}
		throw new NoMatchingFlightCodeException(code);		
	}
	
	protected int getNumberOfEntries() {
		return flights.size();
	}
	
	public void add(Flight flight) {
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
	
	public void printFlightList() throws IOException {
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		String passenger_capacity = null;
		String weight_capacity = null;
		String volume_capacity = null;
		try {	
				fw = new FileWriter(FILENAME);
				bw = new BufferedWriter(fw);
				for (Flight f : flights) {
					String flightCode = "Flight: " + f.getFlightCode();
					bw.write(flightCode + "\n");
					
					String fees = "Baggage fees: £" + f.getTotalBaggageFees();
					bw.write(fees + "\n");
					
					String weight = "Total weight: " + f.getTotalWeight() + "kg";
					bw.write(weight + "\n");
					if(f.getMaxWeight()<f.getTotalWeight()) {
						weight_capacity = "Weight capacity exceeded by: "
								+ (f.getTotalWeight() - f.getMaxWeight()) + "kg";
						bw.write(weight_capacity + "\n");
					}
					
					String volume = "Total volume: " + f.getTotalVolume() + "cm3";
					bw.write(volume + "\n");
					
					if(f.getMaxVol()<f.getTotalVolume()) {
						volume_capacity = "Volume capacity exceeded by: "
								+ (f.getTotalVolume() - f.getMaxVol()) + "cm3";
						bw.write(volume_capacity + "\n");
						
					}
//					else if (f.getMaxVol()>f.getTotalVolume()) {
//						volume_capacity = "Space by volume left: "
//								+ (f.getTotalVolume() - f.getMaxVol());
//					}
//					else if (f.getMaxVol()==f.getTotalVolume()) {
//						volume_capacity = "Flight full";
//					}
					
					String passengers = "Total passengers: " + f.getTotalPassengers();
					bw.write(passengers + "\n");
					if (f.getMaxPassengers()<f.getTotalPassengers()) {
						passenger_capacity = "Flight capacity exceeded by: " 
								+ (f.getTotalPassengers() - f.getMaxPassengers());
					}
					else if (f.getMaxPassengers()>f.getTotalPassengers()) {
						passenger_capacity = "Vacant seats: " + (f.getMaxPassengers()-f.getTotalPassengers());
					}
					else if (f.getMaxPassengers()==f.getTotalPassengers()) {
						passenger_capacity = "Flight full";
					}
					bw.write(passenger_capacity + "\n");
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
