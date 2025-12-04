package in.co.rays.proj4.exception;

/**
 * DatabaseException is used to handle database related errors.
 * 
 * It is thrown when JDBC or SQL operation fails.
 * 
 * Example:
 * - Connection failures
 * - SQL syntax errors
 * - Transaction errors
 * 
 * @author Deepak Verma
 * @version 1.0
 */
public class DatabaseException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with message.
	 *
	 * @param msg error message
	 */
	public DatabaseException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with message and root cause.
	 *
	 * @param msg error message
	 * @param cause root cause exception
	 */
	public DatabaseException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
