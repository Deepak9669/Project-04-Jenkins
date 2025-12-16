package in.co.rays.proj4.bean;

import java.util.Date;

public class DoctorBean extends BaseBean {

	private String name;
	private Date dateOfBirth;
	private String mobile;
	private String expertise;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	@Override
	public String getValue() {
		return expertise;
	}

	@Override
	public String toString() {
		return "DoctorBean [name=" + name + ", dateOfBirth=" + dateOfBirth + ", mobile=" + mobile + ", expertise="
				+ expertise + "]";
	}

	@Override
	public String getKey() {
		return expertise;
	}

}
