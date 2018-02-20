package airport;

public class InvalidParameterException extends Exception{
	public InvalidParameterException (String name) {
		super("Invalid name: Name should be a string");
	}
}
