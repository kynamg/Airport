/*
Passenger List Class
*/

import java.util.HashSet;

public class PassengerList {
	private HashSet<Passenger> passengerList;
	
	//initialise passenger list
	public PassengerList() {
		passengerList = new HashSet<Passenger>();	
	}
	
	public Passenger findBookingRef(String bookingRef) {
		for (Passenger p : passengerList) {
			if(p.getBookingRef().equals(bookingRef)) {
				return p;
			}
		}
		return null;
	}
	
	public int getSizeOfList() {
		return passengerList.size();
	}

}
