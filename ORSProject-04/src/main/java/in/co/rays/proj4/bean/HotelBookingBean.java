package in.co.rays.proj4.bean;

import java.util.Date;

public class HotelBookingBean extends BaseBean {
	
	private String guestName;
	private String roomNb;
	private Date checkinDate;
	private Date checkOutDate;
	private String numberOfGuest;
	private String bookingAmount;
	private String bookingStatus;
	@Override
	public String getKey() {
		return id+"";
	}
	@Override
	public String getValue() {
		return guestName;
	}
	public String getRoomNb() {
		return roomNb;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public void setRoomNb(String roomNb) {
		this.roomNb = roomNb;
	}
	public Date getCheckinDate() {
		return checkinDate;
	}
	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public String getNumberOfGuest() {
		return numberOfGuest;
	}
	public void setNumberOfGuest(String numberOfGuest) {
		this.numberOfGuest = numberOfGuest;
	}
	public String getBookingAmount() {
		return bookingAmount;
	}
	public void setBookingAmount(String bookingAmount) {
		this.bookingAmount = bookingAmount;
	}
	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	

}
