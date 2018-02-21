package tests;

import airport.*;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class flightTest {
	
	@Test
	public void testGetFlightCode() throws InvalidFlightCodeException, InvalidParameterException {
		String expected1 = "LG1234A";
		String message1 = "Failed";
		Flight flight = new Flight("LG1234A", "Illinois", "Lufthansa", 23, 5, 70);
		String actual1 = flight.getFlightCode();
		assertEquals(message1, expected1, actual1);
	}
		
}
