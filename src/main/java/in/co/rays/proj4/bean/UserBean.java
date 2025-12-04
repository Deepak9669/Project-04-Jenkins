package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * UserBean represents user information in the system.
 * It is used for authentication, authorization and user profile data.
 * 
 * Implements DropdownListBean through BaseBean.
 * 
 * @author Deepak Verma
 * @version 1.0
 */
public class UserBean extends BaseBean {

	private String firstName;
	private String lastName;
	private String login;
	private String password;
	private String confirmPassword;
	private Date dob;
	private String mobileNo;
	private long roleId;
	private String gender;

	/**
	 * Gets first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets login (email / username).
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets login (email / username).
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets user password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets user password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets confirm password.
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Sets confirm password.
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * Gets date of birth.
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Sets date of birth.
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Gets mobile number.
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets mobile number.
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Gets role ID.
	 */
	public long getRoleId() {
		return roleId;
	}

	/**
	 * Sets role ID.
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	/**
	 * Gets gender.
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets gender.
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Dropdown key (id).
	 */
	@Override
	public String getKey() {
		return String.valueOf(id);
	}

	/**
	 * Dropdown display value (full name).
	 */
	@Override
	public String getValue() {
		return firstName + " " + lastName;
	}
}
