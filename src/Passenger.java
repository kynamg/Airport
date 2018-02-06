/*
Passenger Class
*/

import java.io.*;
public class Passenger {
	private String name;
	private String surname;
	private String bookingRef;
	private String flightCode;
	private boolean checkedIn;
		
	public Passenger(String n, String s, String bR, String f, boolean cI) {
			name = n;
			surname = s;
			bookingRef = bR;
			flightCode = f;
			checkedIn = cI;
	}
	
	//return the passenger's first name
	public String getName() {
		return name;
	}
	
	//return passenger's last name
	public String getSurname() {
		return surname;
	}
	
	//return booking reference
	public String bookingRef() {
		return bookingRef;
	}
	
	//return flight code
	public String flightCode() {
		return flightCode;
	}
	
	//return whether passenger is checked in or not
	
	public boolean checkedIn() {
		return checkedIn;
	}
	
	
}
