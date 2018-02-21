package airport;
import java.util.HashSet;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FlightList {
	
	private static final String FILENAME = "FlightReport.txt";
	private HashSet<Flight> flights;
	
	// Create HashSet to store flights in 
	public FlightList() {
		flights = new HashSet<Flight>();
	}
	
	// Find flight by flight code
	public Flight findByCode(String code) throws NoMatchingFlightCodeException{
		for (Flight f : flights) {
			if(f.getFlightCode().equals(code)) {
				return f;
			}
		}
		throw new NoMatchingFlightCodeException(code);		
	}
	
	// Return size of Flight List
	protected int getNumberOfEntries() {
		return flights.size();
	}
	
	// Add flight to Flight List
	public void add(Flight flight) {
		flights.add(flight);
	}
	
	// Print flight report
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
					// Print flight code
					String flightCode = "Flight: " + f.getFlightCode();
					bw.write(flightCode + "\n");
					
					// Print total baggage fees
					String fees = "Baggage fees: £" + f.getTotalBaggageFees();
					bw.write(fees + "\n");
					
					// Print total weight of baggage and whether capacity is exceeded
					String weight = "Total weight: " + f.getTotalWeight() + "kg";
					bw.write(weight + "\n");
					if(f.getMaxWeight()<f.getTotalWeight()) {
						weight_capacity = "Weight capacity exceeded by: "
								+ (f.getTotalWeight() - f.getMaxWeight()) + "kg";
						bw.write(weight_capacity + "\n");
					}
					
					// Print total volume of baggage and whether capacity is exceeded
					String volume = "Total volume: " + f.getTotalVolume() + "cm3";
					bw.write(volume + "\n");
					
					if(f.getMaxVol()<f.getTotalVolume()) {
						volume_capacity = "Volume capacity exceeded by: "
								+ (f.getTotalVolume() - f.getMaxVol()) + "cm3";
						bw.write(volume_capacity + "\n");
						
					}
					
					// Print number of total passengers and whether capacity is exceeded
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
		
}
