package exception;

public class QueueExceptions {

	
	public class QueueExceededSizeException extends RuntimeException {		
		private static final long serialVersionUID = 1L;
		public QueueExceededSizeException(String errMessage) {
			super(errMessage);
		}
	}
	
	public class QueueEmptyException extends RuntimeException {		
		private static final long serialVersionUID = 1L;
		public QueueExceededSizeException(String errMessage) {
			super(errMessage);
		}
	}
	

}
