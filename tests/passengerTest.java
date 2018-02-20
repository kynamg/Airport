package tests;

import airport.*;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class passengerTest {
	
	@Test
	void testGetSizeOfList_shouldPass() {
		PassengerList passengerList = new PassengerList();
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
