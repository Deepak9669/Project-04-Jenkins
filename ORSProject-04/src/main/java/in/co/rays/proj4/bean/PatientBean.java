package in.co.rays.proj4.bean;

import java.util.Date;

public class PatientBean extends BaseBean {

	private String name;
	private Date dateOfVisit;
	private String mobile;
	private String disease;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfVisit() {
		return dateOfVisit;
	}

	public void setDateOfVisit(Date dateOfVisit) {
		this.dateOfVisit = dateOfVisit;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	@Override
	public String getValue() {
		return disease;
	}

	@Override
	public String toString() {
		return "PatientBean [name=" + name + ", dateOfVisit=" + dateOfVisit + ", mobile=" + mobile + ", disease="
				+ disease + "]";
	}

	@Override
	public String getKey() {
		return disease;
	}

}
