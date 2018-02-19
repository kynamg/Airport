package tests;

import static org.junit.Assert.*;
import org.junit.*;
import airport.*;

class passengerListTest {
	private PassengerList passengerList;
	@Before
	public void setUp() throws InvalidFlightCodeException, InvalidBookingRefException {
		passengerList = new PassengerList();
		passengerList.addPassenger(new Passenger("Yola", "Jones", "AB12345", "RA8008", false));
	}
	
	@After
	public void tearDown() {
		passengerList = new PassengerList();
	}

	@Test
	void testGetSizeOfList_shouldPass() {
		assertEquals(1, passengerList.getSizeOfList());
		
	}

}
