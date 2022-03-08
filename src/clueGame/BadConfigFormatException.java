package clueGame;

public class BadConfigFormatException extends Exception {
	public BadConfigFormatException() {
		super("Bad config format!");
	}
	
	public BadConfigFormatException(String s) {
		super(s);
	}
}
