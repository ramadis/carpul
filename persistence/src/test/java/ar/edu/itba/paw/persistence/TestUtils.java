package ar.edu.itba.paw.persistence;

import java.sql.Timestamp;

import ar.edu.itba.paw.models.History;
import ar.edu.itba.paw.models.Position;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public class TestUtils {	
	
	private TestUtils() {}
	
	public static class UserUtils {
		public static final String PASSWORD = "Password";
		public static final String USERNAME = "Username";
		public static final String FIRST_NAME = "First";
		public static final String LAST_NAME = "Last";
		public static final Integer ID = 1;
		public static final String PHONE_NUMBER = "phone";
		public static final Timestamp NOW = new Timestamp(System.currentTimeMillis());
		
		public static final String EXISTING_USERNAME = "Username1";
		public static final String NONEXISTING_USERNAME = "Username20";
		public static final Integer EXISTING_ID = 1;
		public static final Integer NONEXISTING_ID = 900;
		
		private UserUtils () {}
		
		public static User sampleUser() {
			// Create test user
			User user = new User();
			user.setUsername(USERNAME);
			user.setPassword(PASSWORD);
			user.setFirst_name(FIRST_NAME);
			user.setLast_name(LAST_NAME);
			user.setPhone_number(PHONE_NUMBER);
			user.setCreated(NOW);
			return user;
		}
	}
	
	public static class TripUtils {
		public static final Integer ID = 1;
		public static final String TO_CITY = "To city";
		public static final String FROM_CITY = "From city";
		public static final Integer SEATS = 4;
		public static final Integer DRIVER_ID = 0;
		public static final Double COST = 99.5;
		public static final Timestamp ETA = new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 5);
		public static final Timestamp ETD = new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 5);
		public static final Double DEPARTURE_LAT = -34.536274;
		public static final Double DEPARTURE_LON = -58.486027;
		public static final Double ARRIVAL_LAT = -34.609475;
		public static final Double ARRIVAL_LON = -58.388214;
		public static final Timestamp NOW = new Timestamp(System.currentTimeMillis());
		
		public static final Integer EXISTING_ID = 1;
		public static final Integer EXISTING_DRIVER_ID = 2;
		public static final Integer EXISTING_PASSENGER_ID = 3;
		public static final Integer NONEXISTING_PASSENGER_ID = 1;
	
		private TripUtils () {}
		
		public static Trip sampleTrip() {
			// Create test trip
			Trip trip = new Trip();
			trip.setTo_city(TO_CITY);
			trip.setFrom_city(FROM_CITY);
			trip.setSeats(SEATS);
			trip.setDriver_id(DRIVER_ID);
			trip.setCost(COST);
			trip.setEta(ETA);
			trip.setCreated(NOW);
			trip.setEtd(ETD);
			
			Position departure = new Position(DEPARTURE_LAT, DEPARTURE_LON);
			Position arrival = new Position(ARRIVAL_LAT, ARRIVAL_LON);
			
			trip.setArrival(arrival);
			trip.setDeparture(departure);
			return trip;
		}
	}
	
	public static class ReviewUtils {
		public static final Integer ID = 1;
		public static final Integer OWNER_ID = 1;
		public static final Integer REVIEWED_ID = 1;
		public static final Integer TRIP_ID = 1;
		public static final Integer STARS = 3;
		public static final String MESSAGE = "Message";
		public static final Timestamp NOW = new Timestamp(System.currentTimeMillis());
	
		private ReviewUtils () {}
		
		public static Review sampleReview(User owner, User reviewed, Trip trip) {
			// Create test review
			Review review = new Review();
			
			review.setOwner(owner);
			review.setReviewedUser(reviewed);
			review.setStars(STARS);
			review.setCreated(NOW);
			review.setTrip(trip);
			review.setMessage(MESSAGE);

			return review;
		}
	}
	
	public static class HistoryUtils {
		public static final Integer USER_ID = 1;
		public static final Integer TRIP_ID = 1;
		public static final String TYPE = "CREATED";
		public static final Timestamp NOW = new Timestamp(System.currentTimeMillis());
	
		private HistoryUtils () {}
		
		public static History sampleHistory() {
			// Create test history
			History history = new History();
			
			history.setRelated(TestUtils.UserUtils.sampleUser());
			history.setTrip(TestUtils.TripUtils.sampleTrip());
			history.setType(TYPE);
			history.setCreated(NOW);
			
			return history;
		}
	}
}
