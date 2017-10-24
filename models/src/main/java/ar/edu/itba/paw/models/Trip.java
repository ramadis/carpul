package ar.edu.itba.paw.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Trip {
	// Mirror fields
	private Integer id;
	private Timestamp etd;
	private Timestamp eta;
	private String from_city;
	private String to_city;
	private Double cost;
	private Position departure;
	private Position arrival;
	private Integer seats;
	private Timestamp created;
	private Integer driver_id;
	
	// Extra convenience fields
	private Integer occupied_seats;
	private Boolean expired;
	private Boolean reserved;
	private List<User> passengers = new ArrayList<>();
	private User driver;
	
	public Trip() {}
	
	public void addPassenger(User p) {
		passengers.add(p);
	}
	
	public List<User> getPassengers() {
		return passengers;
	}
	
	public void setPassengers(List<User> passengers) {
		this.passengers = passengers;
	}
	
	public void toggleReserve() {
		reserved = !reserved;
	}
	
	public void setReserved(Boolean r) {
		reserved = r;
	}
	
	public Boolean getReserved() {
		return reserved;
	}
	
	public void setDriver(User driver) {
		this.driver = driver;
	}
	
	public User getDriver() {
		return driver;
	}

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

	public Double getCost() {
		return cost;
	}


	public void setCost(Double cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "Trip = " + id;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}
	
	
	public String getFrom_city() {
		if (from_city == null || from_city.length() <= 1) return from_city;
		String cap = from_city.substring(0, 1).toUpperCase() + from_city.substring(1);
		return cap;
	}

	public void setFrom_city(String from_city) {
		this.from_city = from_city.toLowerCase();
	}

	public String getTo_city() {
		if (to_city == null || to_city.length() <= 1) return to_city;
		String cap = to_city.substring(0, 1).toUpperCase() + to_city.substring(1);
		return cap;
	}

	public void setTo_city(String to_city) {
		this.to_city = to_city.toLowerCase();
	}

	public Integer getAvailable_seats() {
		return this.seats - occupied_seats;
	}

	public Integer getOccupied_seats() {
		return occupied_seats;
	}

	public void setOccupied_seats(Integer occupied_seats) {
		this.occupied_seats = occupied_seats;
	}

	public Timestamp getEtd() {
		return etd;
	}

	public void setEtd(Timestamp etd) {
		this.etd = etd;
	}
	
	public void setEtd(Integer etd) {
		this.etd = new Timestamp(etd);
	}
	
	public void setEtd(Long etd) {
		this.etd = new Timestamp(etd);
	}

	public Timestamp getEta() {
		return eta;
	}

	public void setEta(Timestamp eta) {
		this.eta = eta;
	}
	
	public void setEta(Long eta) {
		this.eta = new Timestamp(eta);
	}

	public Position getDeparture() {
		return departure;
	}

	public void setDeparture(Position departure) {
		this.departure = departure;
	}

	public Position getArrival() {
		return arrival;
	}

	public void setArrival(Position arrival) {
		this.arrival = arrival;
	}

	public Boolean getExpired() {
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}
}
