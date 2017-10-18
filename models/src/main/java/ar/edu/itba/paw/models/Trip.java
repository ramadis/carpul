package ar.edu.itba.paw.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Trip {
	private Integer id;
	private Integer driver_id;
	private Integer car_id;
	
	//TODO: Remove those ugly temp properties
	private Long etd_temp;
	private Timestamp etd;
	private Timestamp eta;
	private Long eta_temp;
	private String from_city;
	private String to_city;
	private Double cost;
	private Integer seats;
	private Integer occupied_seats;
	private Integer available_seats;
	private Timestamp created;
	private String time_since_reserved;
	private Boolean reserved;
	private String departure_location;
	private String arrival_location;
	private Double cost_per_person;
	private final List<User> passengers = new ArrayList<>();
	private final List<Integer> passengerIds = new ArrayList<>();
	private User driver;
	
	public Trip() {}
	
	public void addPassenger(User p) {
		passengers.add(p);
	}
	
	public List<Integer> getPassengerIds() {
		return passengerIds;
	}
	
	public List<User> getPassengers() {
		return passengers;
	}
	
	public void toggleReserve() {
		reserved = !reserved;
	}
	
	public void addPassenger(Integer p) {
		passengerIds.add(p);
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


	public Integer getCar_id() {
		return car_id;
	}


	public void setCar_id(Integer car_id) {
		this.car_id = car_id;
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

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public String getTime_since_reserved() {
		return time_since_reserved;
	}

	public void setTime_since_reserved(String time_since_reserved) {
		this.time_since_reserved = time_since_reserved;
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
		return available_seats;
	}

	public void setAvailable_seats(Integer available_seats) {
		this.available_seats = available_seats;
	}

	public Integer getOccupied_seats() {
		return occupied_seats;
	}

	public void setOccupied_seats(Integer occupied_seats) {
		this.available_seats = this.seats - occupied_seats;
		if (occupied_seats == 0) this.cost_per_person = this.cost;
		else this.cost_per_person = this.cost / occupied_seats;
		this.occupied_seats = occupied_seats;
	}

	public Double getCost_per_person() {
		return cost_per_person;
	}

	public void setCost_per_person(Double cost_per_person) {
		this.cost_per_person = cost_per_person;
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

	public Timestamp getEta() {
		return eta;
	}

	public void setEta(Timestamp eta) {
		this.eta = eta;
	}

	public Long getEtd_temp() {
		return etd_temp;
	}

	public void setEtd_temp(Long etd_temp) {
		this.setEtd(new Timestamp(etd_temp));
		this.etd_temp = etd_temp;
	}

	public Long getEta_temp() {
		return eta_temp;
	}

	public void setEta_temp(Long eta_temp) {
		this.setEta(new Timestamp(eta_temp));
		this.eta_temp = eta_temp;
	}
}
