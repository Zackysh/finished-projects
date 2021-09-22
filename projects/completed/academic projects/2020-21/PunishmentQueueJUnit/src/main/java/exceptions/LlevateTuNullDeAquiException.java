package exceptions;

/**
 * Exception that extends NullPointerException. <br>
 * Thrown when an application attempts to use null in a case where an object is required.
 * 
 * @author AdriGB
 */
public class LlevateTuNullDeAquiException extends NullPointerException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * No parameters, it has its own message.
	 */
	public LlevateTuNullDeAquiException() {
		super("Llevate tu null. Ahora.");
	}
	 
}
