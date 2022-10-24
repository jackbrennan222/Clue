package clueGame;

public class BadConfigFormatException extends Exception {

	private String badFormat;

	public BadConfigFormatException() {
		super("Error: Bad Format");
	}

	public BadConfigFormatException(String badFormat) {
		super("Error: " + badFormat);
		this.badFormat = badFormat;
	}

	@Override
	public String toString() {
		return "Bad Format: " + badFormat;
	}
}
