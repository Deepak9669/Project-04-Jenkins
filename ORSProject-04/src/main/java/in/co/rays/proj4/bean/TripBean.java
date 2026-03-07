package in.co.rays.proj4.bean;

import java.util.Date;

public class TripBean extends BaseBean {

    private Long tripId;
    private String tripCode;
    private String driverName;
    private Date tripDate;
    private String tripStatus;

    // Default Constructor
    public TripBean() {
    }

    // Getters and Setters

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getTripCode() {
        return tripCode;
    }

    public void setTripCode(String tripCode) {
        this.tripCode = tripCode;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

	@Override
	public String getKey() {
		return id+"";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return tripCode;
	}
}