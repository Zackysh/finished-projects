package exceptions;

/**
 * Exception that extends IllegalStateException. <br>
 * Thrown when a method call is invalid for the object's current state.
 * 
 * @author AdriGB
 */
public class ElementBlockedException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	/**
	 * No parameters, it has its own message.
	 */
	public ElementBlockedException() {
		super("Impossible to empty the queue, is not empty or has only one element");
	}

}
