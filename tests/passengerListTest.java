package tests;

import static org.junit.Assert.*;
import org.junit.*;
import airport.*;
import static org.mockito.Mockito.mock;

public class passengerListTest {
	private PassengerList passengerList;
	
    @Test
    public void test_addPassenger_shouldPass() {
   	 passengerList = new PassengerList();
   	 assertEquals("PassengerList should be initialised to 0 at creation", 0, passengerList.getSizeOfList());
   	 Passenger passenger_mocked1 = mock(Passenger.class);
   	 Passenger passenger_mocked2 = mock(Passenger.class);
   	 Passenger passenger_mocked3 = mock(Passenger.class);
   	 passengerList.addPassenger(passenger_mocked1);
   	 passengerList.addPassenger(passenger_mocked2);
   	 passengerList.addPassenger(passenger_mocked3);
   	 assertEquals("PassengerList should be 3 now", 3, passengerList.getSizeOfList());
    }


	@Test
	public void test_getSizeOfList_shouldPass() {
		passengerList = new PassengerList();
		Passenger passenger_mocked1 = mock(Passenger.class);
		Passenger passenger_mocked2 = mock(Passenger.class);
		passengerList.addPassenger(passenger_mocked1);
		passengerList.addPassenger(passenger_mocked2);
		int actual_listsize = passengerList.getSizeOfList();
		int expected_listsize = 2;
		String msg_listsize = "Returned list size";
		assertEquals(msg_listsize, expected_listsize, actual_listsize);
		
	}
	
	@Test
    public void test_findBookingRef_shouldPass() throws NoMatchingBookingRefException, InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
   	 passengerList = new PassengerList();
   	 Passenger p = new Passenger("Yola","Jones","AB12345","BA1234", "true");
   	 passengerList.addPassenger(p);
   	 Passenger actual_bookingRef = passengerList.findBookingRef("AB12345");
   	 String msg_bookingRef = "Should Pass";
   	 assertNotNull(msg_bookingRef, actual_bookingRef);
    }
	
	@Test
	public void test_NoMatchingBookingRefException_shouldFail() throws InvalidBookingRefException, InvalidParameterException, InvalidFlightCodeException {
		try {
			passengerList = new PassengerList();
		   	Passenger p = new Passenger("Yola","Jones","AB12345","BA1234", "true");
		   	passengerList.addPassenger(p);
		   	passengerList.findBookingRef("AB54321");
		   	fail("Expect an exception to be thrown");
		}
		catch(NoMatchingBookingRefException e) {
			assertEquals("Booking Reference Not Found: AB54321", e.getMessage());
		}
	}



}
