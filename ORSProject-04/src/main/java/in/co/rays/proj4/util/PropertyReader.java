package in.co.rays.proj4.util;

import java.util.ResourceBundle;

/**
 * PropertyReader is a utility class used to read messages/values 
 * from the property file: in.co.rays.proj4.bundle.system
 *
 * It also supports dynamic placeholder replacement like:
 * {0}, {1}, {2}...
 *
 * Example:
 * error.require = {0} is required
 * error.multipleFields = {0} and {1} are mandatory
 *
 * @author Deepak Verma
 * @version 1.0
 */
public class PropertyReader {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");

	/**
	 * Returns value of a key from property file.
	 * If key does not exist, returns the key itself.
	 *
	 * @param key property key
	 * @return value from property file
	 */
	public static String getValue(String key) {

		try {
			return rb.getString(key);
		} catch (Exception e) {
			return key; // fallback
		}
	}

	/**
	 * Returns value with single placeholder replacement.
	 * Example: "error.require" + "loginId"
	 *
	 * @param key property key
	 * @param param parameter to replace {0}
	 * @return formatted message
	 */
	public static String getValue(String key, String param) {
		String msg = getValue(key);
		return msg.replace("{0}", param);
	}

	/**
	 * Returns value with multiple placeholder replacement.
	 * Example: {0}, {1}, {2}...
	 *
	 * @param key property key
	 * @param params array of parameters
	 * @return formatted message
	 */
	public static String getValue(String key, String[] params) {
		String msg = getValue(key);

		for (int i = 0; i < params.length; i++) {
			msg = msg.replace("{" + i + "}", params[i]);
		}

		return msg;
	}

	/**
	 * For testing purpose
	 */
	public static void main(String[] args) {

		System.out.println("Single key example:");
		System.out.println(PropertyReader.getValue("error.require"));

		System.out.println("\nSingle parameter replacement example:");
		System.out.println(PropertyReader.getValue("error.require", "Login Id"));

		System.out.println("\nMultiple parameter replacement example:");
		String[] params = { "Roll No", "Student Name" };
		System.out.println(PropertyReader.getValue("error.multipleFields", params));
	}
}
