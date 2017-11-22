package ar.edu.itba.paw.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserDaoJdbc;

import static org.junit.Assert.*;

import java.util.List;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class TripDaoJdbcTest {
	@Autowired
	private DataSource ds;
	
	@Autowired
	private TripDaoHibernate tripDao;
	
	@Autowired
	private UserDaoJdbc userDao;
	
	private JdbcTemplate jdbcTemplate;
	private Trip testTrip;
	
	@Before
	public void setUp() {
		testTrip = TestUtils.TripUtils.sampleTrip();
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "trips");
		jdbcTemplate.execute("TRUNCATE TABLE trips RESTART IDENTITY;");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
		jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY;");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "trips_users");
		jdbcTemplate.execute("TRUNCATE TABLE trips_users RESTART IDENTITY;");
		userDao.create(TestUtils.UserUtils.sampleUser());
	}
	
	public void assertTrip(Trip trip) {
		assertNotNull(trip);
		assertEquals(TestUtils.TripUtils.TO_CITY, trip.getTo_city());
		assertEquals(TestUtils.TripUtils.FROM_CITY, trip.getFrom_city());
		assertEquals(TestUtils.TripUtils.SEATS, trip.getSeats());
		assertEquals(TestUtils.TripUtils.COST, trip.getCost());
		assertEquals(TestUtils.TripUtils.ETA, trip.getEta());
		assertEquals(TestUtils.TripUtils.ETD, trip.getEtd());
		assertEquals(TestUtils.TripUtils.DEPARTURE_LAT, trip.getDeparture().getLatitude());
		assertEquals(TestUtils.TripUtils.DEPARTURE_LON, trip.getDeparture().getLongitude());
		assertEquals(TestUtils.TripUtils.ARRIVAL_LAT, trip.getArrival().getLatitude());
		assertEquals(TestUtils.TripUtils.ARRIVAL_LON, trip.getArrival().getLongitude());
		assertNotNull(trip.getCreated());
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "trips"));
	}
	
	@Test
	public void testCreate() {
		Trip trip = testTrip;
		User user = TestUtils.UserUtils.sampleUser();

		// Create trip
		final Trip createdTrip = tripDao.create(trip, user);
		
		// Asserts for trip
		assertTrip(createdTrip);
	}
	
	@Test
	public void testReserve() {
		Trip trip = testTrip;
		User user = TestUtils.UserUtils.sampleUser();
		
		// Create trip
		tripDao.create(trip, user);
		
		// Reserve trip
		tripDao.reserveTrip(TestUtils.TripUtils.ID, user);
		
		// Get trip
		Trip reservedTrip = tripDao.findById(TestUtils.TripUtils.ID);
		
		// Assert trip
		assertFalse(reservedTrip.getReserved());
	}
	
	@Test
	public void testUnreserve() {
		User user = TestUtils.UserUtils.sampleUser();
		
		// Reserve trip
		testReserve();
		
		// Unreserve trip
		tripDao.unreserveTrip(TestUtils.TripUtils.ID, user);
		
		// Get trip
		Trip reservedTrip = tripDao.findById(TestUtils.TripUtils.ID);
		
		// Assert trip
		assertFalse(reservedTrip.getReserved());
	}
	
	@Test
	public void testDelete() {
		User user = TestUtils.UserUtils.sampleUser();
		
		// Create trip
		testCreate();
		
		// Delete trip
		tripDao.delete(TestUtils.TripUtils.ID, user);
		
		// Get trip
		Trip reservedTrip = tripDao.findById(TestUtils.TripUtils.ID);
		
		// Assert trip
		assertNull(reservedTrip.getId());
	}
	
	@Test
	public void testGetUserTrips() {
		User user = TestUtils.UserUtils.sampleUser();
		
		// Create trip
		testCreate();
		
		// Get trips for users
		List<Trip> trips = tripDao.getUserTrips(user);
		
		// Assert trip
		assertTrue(trips.size() > 0);
	}
	
	@Test
	public void testGetReservedTrips() {
		User user = TestUtils.UserUtils.sampleUser();
		
		// Create trip
		testCreate();
		
		// Reserve trip
		testReserve();
		
		// Get trips for users
		List<Trip> trips = tripDao.getReservedTrips(user);
		
		// Assert trip
		assertEquals(1, trips.size());
	}
	
	
}