package airport;
//Kyna Mowat-Gosnell, H00147672
public class InvalidFlightCodeException extends Exception{
	public InvalidFlightCodeException (String flightCode) {
		super("Invalid Flight Code In Text File: " + flightCode);
	}

}
