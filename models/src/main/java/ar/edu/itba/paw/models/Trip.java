package ar.edu.itba.paw.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "trips")
public class Trip {
	// Mirror fields
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trips_id_seq")
	@SequenceGenerator(sequenceName = "trips_id_seq", name = "trips_id_seq", allocationSize = 1)
	private Integer id;
	
	@Column
	private Timestamp etd;
	
	@Column
	private Timestamp eta;
	
	@Column(length = 100)
	private String from_city;
	
	@Column(length = 100)
	private String to_city;
	
	@Column
	private Double cost;
	
	@Column
	private Integer seats;
	
	@Column
	private Boolean deleted;
	
	@Column
	private Timestamp created;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="driver_id")
	private User driver;
	
	@Transient
	private Integer driver_id;
	
	// Extra convenience fields
	@Transient
	private Integer occupied_seats;
	
	@Transient
	private Boolean expired;
	
	@Transient
	private Position departure;
	
	@Transient
	private Position arrival;
	
	@Transient
	private Boolean reserved;
	
	@ManyToMany
    @JoinTable( name = "trips_users",
            		joinColumns = @JoinColumn(name = "trip_id"),
            		inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> passengers;
	
	public Trip() {
		super();
	}
	
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
