package in.co.rays.proj4.bean;

/**
 * CollegeBean represents the College entity.
 * <p>
 * It extends {@link BaseBean} and contains college-related details like
 * name, address, state, city and phone number.
 * It also implements dropdown key-value mapping.
 *
 * @author Deepak
 * @version 1.0
 */
public class CollegeBean extends BaseBean {

	private String name;
	private String address;
	private String state;
	private String city;
	private String phoneNo;

	/**
	 * Gets the college name.
	 * 
	 * @return college name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the college name.
	 * 
	 * @param name the college name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the college address.
	 * 
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the college address.
	 * 
	 * @param address the college address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the state of the college.
	 * 
	 * @return state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state of the college.
	 * 
	 * @param state the state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the city of the college.
	 * 
	 * @return city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city of the college.
	 * 
	 * @param city the city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the phone number of the college.
	 * 
	 * @return phone number
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * Sets the phone number of the college.
	 * 
	 * @param phoneNo the phone number
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * Returns key value for dropdown list.
	 * 
	 * @return id as String
	 */
	@Override
	public String getKey() {
		return String.valueOf(id);
	}

	/**
	 * Returns display value for dropdown list.
	 * 
	 * @return college name
	 */
	@Override
	public String getValue() {
		return name;
	}
}
