package tests;

import airport.*;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class passengerTest {
	private Passenger p;
	
	@Test
	public void test_getSurname_shouldPass() throws InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
		p = new Passenger("Yola","Jones","AB12345","BA1234", "true");
		String actual_surname = p.getSurname();
		String expected_surname = "Jones";
		String msg_getsurname = "Should return Jones";
		assertEquals(msg_getsurname, expected_surname, actual_surname);
	}
	
	@Test
	public void test_getBookingRef_shouldPass() throws InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
		p = new Passenger("Yola","Jones","AB12345","BA1234", "true");
		String actual_bookingRef = p.getBookingRef();
		String expected_bookingRef = "AB12345";
		String msg_getBookingRef = "Should return AB12345";
		assertEquals(msg_getBookingRef, expected_bookingRef, actual_bookingRef);
	}
	
	@Test
	public void test_InvalidBookingRefException_shouldFail() throws InvalidBookingRefException, InvalidParameterException, InvalidFlightCodeException {
		try {
		   	Passenger p = new Passenger("Yola","Jones","AB123456","BA1234", "true");
		   	fail("Expect an exception to be thrown");
		}
		catch(InvalidBookingRefException e) {
			assertEquals("Invalid Booking Reference In Text File: AB123456", e.getMessage());
		}
	}
}
