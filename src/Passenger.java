import java.util.regex.Pattern;

/*
Passenger Class - Kyna Mowat-Gosnell, H00147672
*/

public class Passenger {
	//instance variables
	private String name;
	private String surname;
	private String bookingRef;
	private String flightCode;
	private boolean checkIn;
	
	//initalise Passenger class
	public Passenger(String n, String s, String bR, String f, boolean check) throws InvalidFlightCodeException, InvalidBookingRefException{
		name = n;
		surname = s;
		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		boolean invalidRef = p.matcher(bR).find();
		if(invalidRef == true || bR.isEmpty() || bR.length()!=7) {
			throw new InvalidBookingRefException(bookingRef);
		}
		else {
			bookingRef = bR;
		}
		boolean invalidFlightCode = p.matcher(f).find();
		if(invalidFlightCode == true || f.isEmpty() || f.length()!=6) {
			throw new InvalidFlightCodeException(flightCode);
		}
		else {
			flightCode = f;
		}		
		checkIn = check;
	}
	
	//return the passenger's first name
	public String getName() {
		return name;
	}
	
	//return passenger's last name - used in KioskGUI class
	public String getSurname() {
		return surname;
	}
	
	//return booking reference  - used in KioskGUI class
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
	
	//set checkIn to true - used in CheckInDemo class
	public void setCheckIn() {
		checkIn = true;
	}
	
	
}
