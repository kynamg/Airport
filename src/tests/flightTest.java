package tests;

import static org.junit.Assert.*;
import org.junit.*;

import airport.*;

class flightTest {
	
	@Test
	public void testGetFlightCode() {
		String expected1 = "LG1234A";
		String message1 = "Failed";
		Flight flight = new Flight("LG1234A", "Illinois", "Lufthansa", 23, 5, 70);
		String actual1 = flight.getFlightCode();
		assertEquals(message1, expected1, actual1);
	}

}