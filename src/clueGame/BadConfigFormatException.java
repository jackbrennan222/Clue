package clueGame;

public class BadConfigFormatException extends Exception {
	public BadConfigFormatException() {
		super("Error: Bad Format");
	}

	public BadConfigFormatException(String badFormat) {
		super("Error: " + badFormat);
	}
}
