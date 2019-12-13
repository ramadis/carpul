package ar.edu.itba.paw.webapp.DTO;

import ar.edu.itba.paw.models.Position;

public class PositionDTO {
	private Double latitude;
	private Double longitude;
	
	public PositionDTO(Position pos) {
		if (pos == null) return;
		this.latitude = pos.getLatitude();
		this.longitude = pos.getLongitude();
	}
	
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	
}
