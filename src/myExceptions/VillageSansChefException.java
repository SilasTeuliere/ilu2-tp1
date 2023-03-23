package myExceptions;

public class VillageSansChefException extends Exception {

	private static final long serialVersionUID = 1L;

	public VillageSansChefException() {
	}

	public VillageSansChefException(String message) {
		super(message);
	}

	public VillageSansChefException(Throwable cause) {
		super(cause);
	}

	public VillageSansChefException(String message, Throwable cause) {
		super(message, cause);
	}

	public VillageSansChefException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
