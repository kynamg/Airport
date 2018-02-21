package airport;
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
	public Passenger(String n, String s, String bR, String f, String check) throws InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException{
		
		Pattern namePattern = Pattern.compile("^[a-zA-Z]");
		Pattern bookingPattern = Pattern.compile("^[a-zA-Z0-9]{7}$");
		Pattern flightPattern = Pattern.compile("^[a-zA-Z]{2}[0-9]{4}$");
		
		boolean validName = namePattern.matcher(n).find();
		boolean validSurname = namePattern.matcher(s).find();
		boolean validFlight = flightPattern.matcher(f).find();
		boolean validBooking = bookingPattern.matcher(bR).find();

		if(validName == true && !n.isEmpty()) {
			name = n;
		}
		else {
			throw new InvalidParameterException(n);
		}
		if(validSurname == true && !s.isEmpty()) {
			surname = s;
		}
		else {
			throw new InvalidParameterException(s);
		}
		
		if(validFlight == true && !f.isEmpty()) {
			flightCode = f;
		}
		else {
			throw new InvalidFlightCodeException(f);
		}
		
		if(validBooking == true || bR.isEmpty()) {
			bookingRef = bR;
		}
		else {
			throw new InvalidBookingRefException(bR);
		}	

		if (check.equals("true"))
		{
			checkIn = true;
		}
		else if (check.equals("false")) {
			checkIn = false;
		}
		else {
			throw new InvalidParameterException(check);
		}

	}
	
	//return the passenger's first name
	protected String getName() {
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
	protected String getFlightCode() {
		return flightCode;
	}
	
	//return whether passenger is checked in or not
	protected boolean getCheckIn() {
		return checkIn;
	}
	
	//set checkIn to true - used in CheckInDemo class
	public void setCheckIn() {
		checkIn = true;
	}
	
	
}
