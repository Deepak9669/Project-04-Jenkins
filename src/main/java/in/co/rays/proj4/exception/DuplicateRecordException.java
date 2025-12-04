package in.co.rays.proj4.exception;

/**
 * DuplicateRecordException is thrown when a duplicate entry
 * is found during insert or update operation.
 * 
 * Example:
 * - Duplicate email
 * - Duplicate roll number
 * - Duplicate course/college name
 * 
 * @author Deepak Verma
 * @version 1.0
 */
public class DuplicateRecordException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with message.
	 * 
	 * @param msg error message
	 */
	public DuplicateRecordException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with message and root cause.
	 * 
	 * @param msg error message
	 * @param cause root exception
	 */
	public DuplicateRecordException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
