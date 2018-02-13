
public class NoMatchingBookingRefException extends Exception {
	public NoMatchingBookingRefException (String bookingRef) {
		super("Invalid Booking Reference: " + bookingRef);
	}

}
