package in.co.rays.proj4.exception;

/**
 * RecordNotFoundException is thrown when a specific record
 * is not found in the database.
 * 
 * Example:
 * - User not found
 * - Roll number not found
 * - Email not registered
 * 
 * @author Deepak Verma
 * @version 1.0
 */
public class RecordNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with error message.
	 * 
	 * @param msg error message
	 */
	public RecordNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with error message and root cause.
	 * 
	 * @param msg error message
	 * @param cause original exception
	 */
	public RecordNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
