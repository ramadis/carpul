package ar.edu.itba.paw.models;

public class Position {
	private Double latitude;
	private Double longitude;

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}
	
	public Boolean isValid () {
		return latitude != null & longitude != null;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Position() {};

	public Position(Double latitude, Double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}


}
