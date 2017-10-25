package ar.edu.itba.paw.persistence;

import java.sql.Timestamp;

import ar.edu.itba.paw.models.Position;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public class TestUtils {	
	public static class UserUtils {
		public static final String PASSWORD = "Password";
		public static final String USERNAME = "Username";
		public static final String FIRST_NAME = "First";
		public static final String LAST_NAME = "Last";
		public static final Integer ID = 0;
		public static final String PHONE_NUMBER = "phone";
		public static final Timestamp NOW = new Timestamp(System.currentTimeMillis());
		
		public static User sampleUser() {
			// Create test user
			User user = new User();
			user.setId(ID);
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
		public static final Integer ID = 0;
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
}
