package in.co.rays.proj4.exception;

/**
 * ApplicationException is a custom exception for handling
 * application-level errors.
 * 
 * This exception is thrown when a business logic failure occurs.
 * 
 * Example:
 * - Database failures
 * - Validation failures
 * - Transaction issues
 * 
 * @author Deepak Verma
 * @version 1.0
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with message.
	 *
	 * @param msg error message
	 */
	public ApplicationException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with message and root cause.
	 *
	 * @param msg error message
	 * @param cause underlying exception
	 */
	public ApplicationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
