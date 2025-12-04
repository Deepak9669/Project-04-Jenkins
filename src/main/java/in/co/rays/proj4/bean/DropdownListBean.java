package in.co.rays.proj4.bean;

/**
 * DropdownListBean is a common interface for all beans
 * which are used in dropdown lists.
 * <p>
 * It provides key-value pair representation for UI dropdowns.
 * 
 * @author Deepak Verma
 * @version 1.0
 */
public interface DropdownListBean {

	/**
	 * Returns the key of the dropdown option.
	 * Generally this is the primary key (ID).
	 * 
	 * @return key as String
	 */
	public String getKey();

	/**
	 * Returns the display value of the dropdown option.
	 * Generally this is the name or title.
	 * 
	 * @return display value as String
	 */
	public String getValue();

}
