package airport;

public class InvalidParameterException extends Exception{
	public InvalidParameterException (Object name) {
		super("Parameter invalid : " + name);
	}
}
