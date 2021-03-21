package exceptions;

import javax.naming.SizeLimitExceededException;

/**
 * Exception that extends SizeLimitExceededException. <br>
 * Thrown when a method produces a result that exceeds a size-related limit.
 * 
 * @author AdriGB
 */
public class QueueExceededSizeException extends SizeLimitExceededException {
	private static final long serialVersionUID = 1L;

	/**
	 * No parameters, it has its own message.
	 */
	public QueueExceededSizeException() {
		super("Max capacity exceeded, operation denied");
	}
}