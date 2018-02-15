package airport;
//Kyna Mowat-Gosnell, H00147672
public class InvalidBookingRefException extends Exception{
	public InvalidBookingRefException (String bookingRef) {
		super("Invalid Booking Reference In Text File: " + bookingRef);
	}

}
