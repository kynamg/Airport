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
	
	
	
//	@Test
//	public void exceptionTesting() {
//		Throwable exception = assertThrows(NoMatchingFlightCodeException.class, () -> { {
//			throw new NoMatchingFlightCodeException("a message");
//		});
//		assertEquals("a message", exception.getMessage());
//	}
	
//	@Test(expected = NoMatchingFlightCodeException.class)
//	public void codeNotFound1() throws NoMatchingFlightCodeException {
//		flightList.findByCode("TF1234R");
//	}
	
//	@Test(expected = NoMatchingFlightCodeException.class)
//	public void codeNotFound() {
//		try{
//			flightList.findByCode("TF1234R");
//			fail("Code does not exist - should throw exception");
//		}
//		catch(NoMatchingFlightCodeException e) {
//			assertTrue(e.getMessage().contains("TF1234R"));
//		}
//		
//	}
	
	
}
	