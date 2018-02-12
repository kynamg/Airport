
public class NoMatchingFlightCodeException extends Exception{
	public NoMatchingFlightCodeException (String flightCode) {
		super("No such flight code: " + flightCode);
	}
}
