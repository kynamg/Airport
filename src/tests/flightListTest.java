package tests;

import static org.junit.Assert.*;
import org.junit.*;

import airport.*;

class flightListTest {

	private FlightList flightList;
	
	@Before
	public void setUp() {
		FlightList flightList = new FlightList();
		flightList.add(new Flight("LG1234A", "Illinois", "Lufthansa", 23, 5, 70));
		flightList.add(new Flight("LG1254A", "Kentucky", "Lufthansa", 23, 6, 70));
		flightList.add(new Flight("AB1234A", "Chicago", "Lufthansa", 23, 2, 70));
	}
	
	@Test(expected = NoMatchingFlightCodeException.class)
	public void codeNotFound1() throws NoMatchingFlightCodeException {
		flightList.findByCode("TF1234R");
	}
	
	@Test(expected = NoMatchingFlightCodeException.class)
	public void codeNotFound() {
		try{
			flightList.findByCode("TF1234R");
			fail("Code does not exist - should throw exception");
		}
		catch(NoMatchingFlightCodeException e) {
			assertTrue(e.getMessage().contains("TF1234R"));
		}
		
	}
}




