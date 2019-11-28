package ar.edu.itba.paw.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {
	@Column(length = 100, nullable = false, unique = true)
	private String username;
	
	@Column(length = 100, nullable = false)
	private String password;
	
	@Column(length = 100)
	private String first_name;
	
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = false, mappedBy = "driver")
	private List<Trip> drived_trips;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="user")
	private List<Reservation> reservations;
	
	@Column(length = 100)
	private String last_name;
	
	@Column(length = 100)
	private String phone_number;
	
	@Column
	private Timestamp created;
	
	@Column
	private byte[] profileImage;
	
	@Column
	private byte[] coverImage;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
	@SequenceGenerator(sequenceName = "users_id_seq", name = "users_id_seq", allocationSize = 1)
	private Integer id;
	
	// METHODS
	
	public byte[] getProfileImage () {
		return profileImage;
	}
	
	public byte[] getCoverImage () {
		return coverImage;
	}
	
	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}
	
	public void setCoverImage(byte[] coverImage) {
		this.coverImage = coverImage;
	}
	
	public String getFirst_name() {
		return first_name;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<Trip> getDrived_trips() {
		return drived_trips;
	}

	public void setDrived_trips(List<Trip> drived_trips) {
		this.drived_trips = drived_trips;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User() {
		this.username = this.password = "";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User = " + first_name;
	}

	public Timestamp getCreated() {
		return created;
	}
	
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	
	public Integer getRating() {
		Integer sum = 0;
		Integer count = 0;
		
		for (Trip trip: this.getDrived_trips()) {
			for (Review review: trip.getReviews()) {
				sum += review.getStars();
				count++;	
			}
		}
		
		return count == 0 ? -1 : Math.round(sum/count);
	}
	
	public boolean equals(Object o) {
		User u = (User) o;
		return u.getUsername().equals(this.getUsername());
	}
	
	public int hashCode() {
		return this.getId();
	}

	public String getDays_since_creation() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Long  ms = now.getTime() - created.getTime();
		Integer days = (int) (ms / (1000*60*60*24));
		return days.toString();
	}
}
