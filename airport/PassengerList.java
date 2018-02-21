package airport;
/*
Passenger List Class - Kyna Mowat-Gosnell, H00147672
*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PassengerList {
	//instance variable
	private HashSet<Passenger> passengerList;
	//initialise PassengerList class
	public PassengerList() {
		passengerList = new HashSet<Passenger>();	
	}
	
	//method takes parameters from Passenger
	protected Passenger findBookingRef(String bookingRef) throws NoMatchingBookingRefException{
		//for a passenger 'p' in the passengerList hash set
		for (Passenger p : passengerList) {
			//if the bookingRef in the hash set is equal to the bookingRef input, return the passenger 'p'
			if(p.getBookingRef().equals(bookingRef)) {
				return p;
			}
		}
		throw new NoMatchingBookingRefException(bookingRef);
		//return null;
	}
	
	//return size of passengerList Hash set
	public int getSizeOfList() {
		return passengerList.size();
	}
	
	public int getPassengersNotCheckedIn() {
		int count = 0;
		for (Passenger p : passengerList) {
			if(!p.getCheckIn()) {
				count ++;
			}
		}
		return count;
	}
	
	//add entry to passengerList hash set
	public void addPassenger(Passenger person) {
		passengerList.add(person);
	}	
}

