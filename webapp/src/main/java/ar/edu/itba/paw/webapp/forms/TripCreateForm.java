package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;

public class TripCreateForm {
	@NotBlank
	@Pattern(regexp = "[a-zA-Z ]+")
	private String from_city;
	
	@NotBlank
	@Pattern(regexp = "[a-zA-Z ]+")
	private String to_city;
	
	@NotNull
	@Min(1)
	@Max(20)
	private Integer seats;
	
	@NotNull
	@Min(0)
	@Max(10000)
	private Double cost;
	
	@NotNull
	private Long etd;
	
	@NotNull
	private Long eta;
	
	
	public String getFrom_city() {
		return from_city;
	}

	public void setFrom_city(String from_city) {
		this.from_city = from_city;
	}

	public String getTo_city() {
		return to_city;
	}

	public void setTo_city(String to_city) {
		this.to_city = to_city;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Long getEtd() {
		return etd;
	}

	public void setEtd(Long etd) {
		this.etd = etd;
	}

	public Long getEta() {
		return eta;
	}

	public void setEta(Long eta) {
		this.eta = eta;
	}
	
	public Trip getTrip() {
		Trip trip = new Trip();
		trip.setEta(eta);
		trip.setEtd(etd);
		trip.setSeats(seats);
		trip.setFrom_city(from_city);
		trip.setTo_city(to_city);
		trip.setCost(cost);
		return trip;
	}
}
