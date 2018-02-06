/*
Passenger Class
*/

import java.io.*;
public class Passenger {
	private String name;
	private String surname;
	private String bookingRef;
	private String flightCode;
	private boolean status;
	//do i need a variable for checking in?
		
	public Passenger(String n, String s, String bR, String f, boolean stat) {
			name = n;
			surname = s;
			bookingRef = bR;
			flightCode = f;
			status = stat;
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
	public String getbookingRef() {
		return bookingRef;
	}
	
	//return flight code
	public String getflightCode() {
		return flightCode;
	}
	
	//return status checked in of passenger
	public boolean getStatus() {
		return status;
	}
	
	//method to check in passengers
	
}
