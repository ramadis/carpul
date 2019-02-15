package ar.edu.itba.paw.webapp.DTO;

import java.util.List;

public class ResultDTO {
	private List<TripDTO> trips;
	private List<TripDTO> later_trips;
	
	public ResultDTO (List<TripDTO> trips, List<TripDTO> later_trips) {
		this.later_trips = later_trips;
		this.trips = trips;
	}
	
	public List<TripDTO> getTrips() {
		return trips;
	}
	public void setTrips(List<TripDTO> trips) {
		this.trips = trips;
	}
	public List<TripDTO> getLater_trips() {
		return later_trips;
	}
	public void setLater_trips(List<TripDTO> later_trips) {
		this.later_trips = later_trips;
	}
	
	
}
