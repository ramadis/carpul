package ar.edu.itba.paw.models;

public class Trip {
	private Integer id;
	private Integer driver_id;
	private Integer car_id;
	private String etd;
	private String eta;
	private Double cost;
	private String departure_location;
	private String arrival_location;
	

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getDriver_id() {
		return driver_id;
	}


	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}


	public Integer getCar_id() {
		return car_id;
	}


	public void setCar_id(Integer car_id) {
		this.car_id = car_id;
	}


	public String getEtd() {
		return etd;
	}


	public void setEtd(String etd) {
		this.etd = etd;
	}


	public String getEta() {
		return eta;
	}


	public void setEta(String eta) {
		this.eta = eta;
	}


	public Double getCost() {
		return cost;
	}


	public void setCost(Double cost) {
		this.cost = cost;
	}


	public String getDeparture_location() {
		return departure_location;
	}


	public void setDeparture_location(String departure_location) {
		this.departure_location = departure_location;
	}


	public String getArrival_location() {
		return arrival_location;
	}


	public void setArrival_location(String arrival_location) {
		this.arrival_location = arrival_location;
	}


	@Override
	public String toString() {
		return "Trip = " + id;
	}
}
