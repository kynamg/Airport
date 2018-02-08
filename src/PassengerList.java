/*
Passenger List Class - Kyna Mowat-Gosnell, H00147672
*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class PassengerList {
	//instance variable
	private HashSet<Passenger> passengerList;
	//initialise PassengerList class
	public PassengerList() {
		passengerList = new HashSet<Passenger>();	
	}
	
	//method takes parameters from Passenger
	public Passenger findBookingRef(String bookingRef) {
		//for a passenger 'p' in the passengerList hash set
		for (Passenger p : passengerList) {
			//if the bookingRef in the hash set is equal to the bookingRef input, return the passenger 'p'
			if(p.getBookingRef().equals(bookingRef)) {
				return p;
			}
		}
		return null;
	}
	
	//return size of passengerList Hash set
	public int getSizeOfList() {
		return passengerList.size();
	}
	
	//add entry to passengerList hash set
	public void addPassenger(Passenger person) {
		passengerList.add(person);
	}
	
	//print list of passengers - iterate through list and print each passenger
	public void printList(Passenger p) {
		for(int i =0; i > passengerList.size(); i++) {
			System.out.println(p.getName() + p.getSurname() + p.getBookingRef() + p.getFlightCode() + p.getCheckIn());
		}
	}		
}

