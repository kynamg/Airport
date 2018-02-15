package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import airport.*;

class flightListTest {

	@Before
	public void setUp() {
		FlightList flightList = new FlightList();
	}
	
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}

//Just for testing
//public static void main(String[] args) throws IOException {
//	FlightList flights1 = new FlightList();
//	Flight f1 = new Flight("L123G", "Illinois", "Lufthansa", 23, 5, 70);
//	Flight f2 = new Flight("L124G", "Kentucky", "Lufthansa", 23, 6, 70);
//	Flight f3 = new Flight("L125G", "Chicago", "Lufthansa", 23, 2, 70);
//	f2.incrementBaggageFees((float) 34.634);
//	f1.incrementPassengers();
//	f2.incrementPassengers();
//	f3.incrementPassengers();
//	f3.incrementPassengers();
//	f3.incrementPassengers();
//	f3.incrementBaggageFees((float) 34.634);
//	f3.incrementVolume((float) 99.56);
//	f3.incrementVolume((float) 31.44);
//	f3.incrementWeight((float) 128.56);
//	flights1.add(f1);
//	flights1.add(f2);
//	flights1.add(f3);
//	//flights1.findByCode("L134G");
//	flights1.getNumberOfEntries();
//	flights1.printFlightList();
//}

// This is the method to be tested:
//public Flight findByCode(String code) throws NoMatchingFlightCodeException{
//	for (Flight f : flights) {
//		if(f.getFlightCode().equals(code)) {
//			System.out.println("Flight found");
//			return f;
//		}
//	}
//	throw new NoMatchingFlightCodeException(code);		
//}


