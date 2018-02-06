/*
Passenger Class
*/

public class Passenger {
	private String name;
	private String surname;
	private String bookingRef;
	private String flightCode;
	private boolean checkIn;
		
	public Passenger(String n, String s, String bR, String f, boolean check) {
			name = n;
			surname = s;
			bookingRef = bR;
			flightCode = f;
			checkIn = check;
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
	public String getBookingRef() {
		return bookingRef;
	}
	
	//return flight code
	public String getFlightCode() {
		return flightCode;
	}
	
	//return whether passenger is checked in or not
	public boolean getCheckIn() {
		return checkIn;
	}
	
	public void setCheckIn() {
		checkIn = true;
	}
	
	
}
