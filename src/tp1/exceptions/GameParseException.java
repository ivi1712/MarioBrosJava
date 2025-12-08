package tp1.exceptions;

public class GameParseException extends GameModelException {

	public GameParseException() {
		
	}

	public GameParseException(String message) {
		super(message);
		
	}

	public GameParseException(Throwable cause) {
		super(cause);
		
	}

	public GameParseException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public GameParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
