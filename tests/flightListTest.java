package tests;

import airport.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class flightListTest {
	

	private FlightList flightList;

	@Before
	public void setUp() throws InvalidFlightCodeException, InvalidParameterException {
		FlightList flightList = new FlightList();
		flightList.add(new Flight("LG1234A", "Illinois", "Lufthansa", 23, 5, 70));
		flightList.add(new Flight("LG1254A", "Kentucky", "Lufthansa", 23, 6, 70));
		flightList.add(new Flight("AB1234A", "Chicago", "Lufthansa", 23, 2, 70));
	}
	
	// Test to throw exception NoMatchingFlightCodeException
	@Test
	public void exceptionTesting() throws InvalidFlightCodeException, InvalidParameterException {
		try {
			FlightList flightList1 = new FlightList();
			flightList1.add(new Flight("LG1234A", "Illinois", "Lufthansa", 23, 5, 70));
			flightList1.findByCode("LG1234");
			fail("Expected an exception to be thrown");
			
		} catch(NoMatchingFlightCodeException e) {
			assertEquals("No such flight code: LG1234", e.getMessage());
		}
	}
	
	// Test to method to find flight code
	@Test
	public void testFindByCode() throws InvalidFlightCodeException, InvalidParameterException, NoMatchingFlightCodeException{
		Flight flight1 = new Flight("BA12345", "Kentucky", "Lufthansa", 23, 5, 70);
		FlightList flightList1 = new FlightList();
		flightList1.add(flight1);
		Flight f_found = flightList1.findByCode("BA12345");
		String message_error = "Failed at BA12345";
		assertNotNull(message_error, f_found);
	}
}