package tp1.exceptions;

public class NoAvaibleCreateException extends GameModelException {

	public NoAvaibleCreateException() {
		
	}

	public NoAvaibleCreateException(String message) {
		super(message);
		
	}

	public NoAvaibleCreateException(Throwable cause) {
		super(cause);
		
	}

	public NoAvaibleCreateException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public NoAvaibleCreateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
