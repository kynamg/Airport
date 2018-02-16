package tests;

import static org.junit.Assert.*;
import org.junit.*;
import airport.*;

class passengerListTest {
	private PassengerList passengerList;
	@Before
	public void setUp() throws InvalidFlightCodeException, InvalidBookingRefException {
		passengerList = new PassengerList();
		passengerList.addPassenger(new Passenger(Yola, Jones, null, null, false));
	}
	
	@After
	public void tearDown() {
		passengerList = new PassengerList();
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
