//Kyna Mowat-Gosnell, H00147672
public class NoMatchingBookingRefException extends Exception {
	public NoMatchingBookingRefException (String bookingRef) {
		super("Booking Reference Not Found: " + bookingRef);
	}

}
