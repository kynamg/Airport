package tests;

import airport.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class flightTest {
	
	// Test to throw InvalidFlightCodeException
	@Test
	public void testInvalidFlightCodeException() throws InvalidFlightCodeException, InvalidParameterException {
		try {
			Flight flight1 = new Flight("wrong", "destination", "carrier", 23, 5, 70);
			fail("Expected an exception to be thrown");
			
		} catch(InvalidFlightCodeException e) {
			assertEquals("Invalid Flight Code In Text File: wrong", e.getMessage());
		}
	}
	
	// Test to throw InvalidParameterException
	@Test
	public void testInvalidParameterException() throws InvalidFlightCodeException, InvalidParameterException {
		try {
			Flight flight2 = new Flight("LA1234", "12414", "carrier", 23, 5, 70);
			fail("Expected an exception to be thrown");
			
		} catch(InvalidParameterException e) {
			assertEquals("Parameter invalid : 12414", e.getMessage());
		}
		try {
			Flight flight2 = new Flight("LA1234", "destination", "1341", 23, 5, 70);
			fail("Expected an exception to be thrown");
			
		} catch(InvalidParameterException e) {
			assertEquals("Parameter invalid : 1341", e.getMessage());
		}
	}

	// Test to get flight code from flight
	@Test
	public void testGetFlightCode() throws InvalidFlightCodeException, InvalidParameterException {
		String expected1 = "LG1234";
		String message1 = "Failed at LG1234";
		Flight flight = new Flight("LG1234", "Illinois", "Lufthansa", 23, 5, 70);
		String actual1 = flight.getFlightCode();
		assertEquals(message1, expected1, actual1);	
	}
	
	// Test calculateExcessBaggageFees method
	@Test
	public void testCalculateExcessBaggageFees() throws InvalidFlightCodeException, InvalidParameterException {
		
		// Input = 20
		Flight f = new Flight("LG1234", "Atlanta", "United Airlines", 23, 5, 70);
		float returned_20 = f.calculateExcessBaggageFees((float) 20.0);
		float expected_20 = 0;
		String message_20 = "Failed at zero";
		assertTrue(message_20 ,expected_20 == returned_20);
		
		// Input = 26
		float returned_correct = f.calculateExcessBaggageFees((float) 26.0);
		float expected_correct = 10;
		String message_correct = "Failed at 26";
		assertTrue(message_correct, expected_correct == returned_correct);
		
		// Input = 25
		float returned_25 = f.calculateExcessBaggageFees((float) 25.0);
		float expected_25 = 0;
		String message_25 = "Failed at 25";
		assertTrue(message_25, expected_25 == returned_25);
		
		// Input = 0
		float returned_zero = f.calculateExcessBaggageFees((float) 0.0);
		float expected_zero = 0;
		String message_zero = "Failed at 26";
		assertTrue(message_zero, expected_zero == returned_zero);
	}	
	
	
}
	