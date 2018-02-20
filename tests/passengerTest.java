package tests;

import static org.junit.Assert.*;
import org.junit.*;
import airport.*;

class passengerTest {
	/*
	 * 
	 * */
	@Before
	public void setUp() throws InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
		Passenger passenger = new Passenger(null, null, null, null, false);
		//add passengers to test in here
	}

	@Test
	void testPassengerClass() throws InvalidParameterException {
		String expected1 = "Kwaume";
		String expected2 = "Nkrumah";
		String expected3 = "BAHD5FF";
		String expected4 = "AHSN32";
		boolean expected5 = true;
		String msg1 = "Failed for name = Kwaume";
		String msg2 = "Failed for surname = Nkrumah";
		String msg3 = "Failed for booking ref =";
		String msg4 = "Failed for flight code = ";
		Passenger test = null;
		try {
			test = new Passenger(msg1, msg2, msg3, msg4, false);
		} catch (InvalidFlightCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidBookingRefException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String actual1 = test.getSurname();
		assertEquals(msg1, expected1, actual1);
	}

}