package in.co.rays.proj4.bean;

public class ClientBean extends BaseBean {

	private String ClientName;
	private String Address;
	private String Phone;
	private String Priortiy;

	public String getClientName() {
		return ClientName;
	}

	public void setClientName(String clientName) {
		ClientName = clientName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getPriortiy() {
		return Priortiy;
	}

	public void setPriortiy(String priortiy) {
		Priortiy = priortiy;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return ClientName;
	}

}
