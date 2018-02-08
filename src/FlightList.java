import java.util.HashSet;
import java.util.Iterator;

public class FlightList {
	
	private HashSet<Flight> flights;
	
	public FlightList() {
		flights = new HashSet<Flight>();
	}
	
	public Flight findByCode(String code) {
		for (Flight f : flights) {
			if(f.getFlightCode().equals(code)) {
				System.out.println("Flight found");
				return f;
			}
		}
		System.out.println("No flight found with that flight code");
		return null;		
	}
	
	public int getNumberOfEntries() {
		return flights.size();
	}
	
	public void add(Flight flight) {
		flights.add(flight);
	}
	
	
	// Just for testing
//	public static void main(String[] args) {
//		FlightList flights1 = new FlightList();
//		Flight f1 = new Flight("L123G", "Illinois", "Lufthansa", 23, 5, 70);
//		Flight f2 = new Flight("L124G", "Kentucky", "Lufthansa", 23, 5, 70);
//		Flight f3 = new Flight("L125G", "Chicago", "Lufthansa", 23, 5, 70);
//		flights1.add(f1);
//		flights1.add(f2);
//		flights1.add(f3);
//		flights1.findByCode("L134G");
//		flights1.getNumberOfEntries();
//	}
}
