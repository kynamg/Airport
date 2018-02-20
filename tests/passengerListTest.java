package tests;

import static org.junit.Assert.*;
import org.junit.*;
import airport.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class passengerListTest {
	private PassengerList passengerList;
	
	/*@Before
	public void setUp() throws InvalidFlightCodeException, InvalidBookingRefException {
		passengerList = new PassengerList();
		Passenger passenger_mocked1 = mock(Passenger.class);
		Passenger passenger_mocked2 = mock(Passenger.class);
		passengerList.addPassenger(passenger_mocked1);
		passengerList.addPassenger(passenger_mocked2);
	}
	
	@After
	public void tearDown() {
		passengerList = new PassengerList();
	}*/

	@Test
	void testGetSizeOfList_shouldPass() {
		passengerList = new PassengerList();
		Passenger passenger_mocked1 = mock(Passenger.class);
		Passenger passenger_mocked2 = mock(Passenger.class);
		passengerList.addPassenger(passenger_mocked1);
		passengerList.addPassenger(passenger_mocked2);
		int actual_listsize = passengerList.getSizeOfList();
		int expected_listsize = 2;
		String msg_listsize = "Returned correct list size?";
		assertEquals(msg_listsize, expected_listsize, actual_listsize);
		
	}

}
