package airport;
import java.util.Random;
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
	private int baggage_weight;
	private int baggage_volume;
	private float baggage_fee;
	
	public Passenger(String n, String s, String bR, String f, String check) throws InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException{
		//initialises regular expression patterns to match character combinations for name/surname, booking ref, and flight code
		Pattern namePattern = Pattern.compile("^[a-zA-Z]");
		Pattern bookingPattern = Pattern.compile("^[a-zA-Z0-9]{7}$");
		Pattern flightPattern = Pattern.compile("^[a-zA-Z]{2}[0-9]{4}$");
		//assigning the patterns to the name, surname, flight code and booking ref variables
		boolean validName = namePattern.matcher(n).find();
		boolean validSurname = namePattern.matcher(s).find();
		boolean validFlight = flightPattern.matcher(f).find();
		boolean validBooking = bookingPattern.matcher(bR).find();
		// if else statements that throw exceptions to ensure the correct info is being input into system
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
		
		//Randomly assign baggage weight on object creation - this may be overwritten
		set_baggage();
		System.out.println("Passenger "+name+" "+surname);
		System.out.println("Baggage weight, volume, fees = "+getBaggageWeight()+" "+getBaggageVolume()+" "+getBaggageFee());
	}
	
	public int getBaggageWeight() {
		return baggage_weight;
	}
	
	private void set_baggage() {
		Random random = new Random();
		baggage_weight = random.nextInt(50);
		baggage_volume = random.nextInt(160);
	}
	
	protected void setBaggageWeight(int baggage_weight) {
		this.baggage_weight = baggage_weight;
	}
	
	public int getBaggageVolume() {
		return baggage_volume;
	}
	
	protected void setBaggageVolume(int baggage_volume) {
		this.baggage_volume = baggage_volume;
	}
	
	protected float getBaggageFee() {
		return baggage_fee;
	}
	
	protected void setBaggageFee(float baggage_fee) {
		this.baggage_fee = baggage_fee;
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
