package tests;
//Written by Kyna Mowat-Gosnell
import static org.junit.Assert.*;
import org.junit.*;
import airport.*;
import static org.mockito.Mockito.mock;

public class passengerListTest {
	private PassengerList passengerList;
	
    @Test
    public void test_addPassenger_shouldPass() {
    	//create passengerList object
    	passengerList = new PassengerList();
    	//make sure passengerlist is initially empty
    	assertEquals("PassengerList should be initialised to 0 at creation", 0, passengerList.getSizeOfList());
    	//mock 3 passengers
    	Passenger passenger_mocked1 = mock(Passenger.class);
   	 	Passenger passenger_mocked2 = mock(Passenger.class);
   	 	Passenger passenger_mocked3 = mock(Passenger.class);
   	 	//add passengers to passengerlist
   	 	passengerList.addPassenger(passenger_mocked1);
   	 	passengerList.addPassenger(passenger_mocked2);
   	 	passengerList.addPassenger(passenger_mocked3);
   	 	//test that the passengers have been added to the list by finding the size of the list and compare to expected value of 3
   	 	assertEquals("PassengerList should be 3 now", 3, passengerList.getSizeOfList());
    }


	@Test
	public void test_getSizeOfList_shouldPass() {
		//create passengerList object
		passengerList = new PassengerList();
		//mock 2 passengers
		Passenger passenger_mocked1 = mock(Passenger.class);
		Passenger passenger_mocked2 = mock(Passenger.class);
		//add passengers to passengerlist
		passengerList.addPassenger(passenger_mocked1);
		passengerList.addPassenger(passenger_mocked2);
		//call getSizeOfList method to get int
		int actual_listsize = passengerList.getSizeOfList();
		//set expected int
		int expected_listsize = 2;
		String msg_listsize = "Returned list size";
		//test that list size returned by getSizeOfList is equal to expected variable
		assertEquals(msg_listsize, expected_listsize, actual_listsize);
		
	}
	
	@Test
    public void test_findBookingRef_shouldPass() throws NoMatchingBookingRefException, InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
		//create passengerList object
		passengerList = new PassengerList();
   	 	//create new Passenger object
		Passenger p = new Passenger("Yola","Jones","AB12345","BA1234", "true");
   	 	//add Passenger to passengerlist
		passengerList.addPassenger(p);
   	 	//call findBookingRef
		Passenger actual_bookingRef = passengerList.findBookingRef("AB12345");
   	 	String msg_bookingRef = "Should Pass";
   	 	//tests that the parsed parameter is not null
   	 	assertNotNull(msg_bookingRef, actual_bookingRef);
    }
	
	@Test
	public void test_NoMatchingBookingRefException_shouldFail() throws InvalidBookingRefException, InvalidParameterException, InvalidFlightCodeException {
		try {
			//create passengerList object
			passengerList = new PassengerList();
		   	//create Passeenger object
			Passenger p = new Passenger("Yola","Jones","AB12345","BA1234", "true");
			//add passenger
			passengerList.addPassenger(p);
			//parse incorrect parameter into findBookingRef and it will throw the exception
		   	passengerList.findBookingRef("AB54321");
		   	fail("Expect an exception to be thrown");
		}
		catch(NoMatchingBookingRefException e) {
			assertEquals("Booking Reference Not Found: AB54321", e.getMessage());
		}
	}
}
