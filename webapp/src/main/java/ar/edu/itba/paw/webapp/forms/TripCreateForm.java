package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import ar.edu.itba.paw.models.Position;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.webapp.forms.validations.TripTimeAnnotation;;

@TripTimeAnnotation( departure = "etd", arrival = "eta")
public class TripCreateForm {
	@NotBlank
	@Pattern(regexp = "[\\p{L} ]+")
	private String from_city;
	
	@NotBlank
	@Pattern(regexp = "[\\p{L} ]+")
	private String to_city;
	
	@NotNull
	private Double etd_latitude;

	@NotNull
	private Double etd_longitude;
	
	@NotNull
	private Double eta_latitude;
	
	@NotNull
	private Double eta_longitude;
	
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
	
	public Double getEtd_latitude() {
		return etd_latitude;
	}

	public void setEtd_latitude(Double etd_latitude) {
		this.etd_latitude = etd_latitude;
	}

	public Double getEtd_longitude() {
		return etd_longitude;
	}

	public void setEtd_longitude(Double etd_longitude) {
		this.etd_longitude = etd_longitude;
	}

	public Double getEta_latitude() {
		return eta_latitude;
	}

	public void setEta_latitude(Double eta_latitude) {
		this.eta_latitude = eta_latitude;
	}

	public Double getEta_longitude() {
		return eta_longitude;
	}

	public void setEta_longitude(Double eta_longitude) {
		this.eta_longitude = eta_longitude;
	}
	
	public Trip getTrip() {
		Trip trip = new Trip();
		trip.setEta(eta);
		trip.setEtd(etd);
		trip.setSeats(seats);
		trip.setFrom_city(from_city);
		trip.setTo_city(to_city);
		trip.setCost(cost);
		trip.setDeleted(false);
		trip.setDeparture_lat(etd_latitude);
		trip.setDeparture_lon(etd_longitude);
		trip.setArrival_lat(eta_latitude);
		trip.setArrival_lon(eta_longitude);
		trip.setDeparture(new Position(etd_latitude, etd_longitude));
		trip.setArrival(new Position(eta_latitude, eta_longitude));
		return trip;
	}
}
