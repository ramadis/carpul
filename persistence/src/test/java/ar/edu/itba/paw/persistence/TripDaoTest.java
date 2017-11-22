package ar.edu.itba.paw.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.TripDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import static org.junit.Assert.*;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class TripDaoTest {
	@Autowired
	private DataSource ds;
	
	@Autowired
	private TripDao tripDao;
	
	@Autowired
	private UserDao userDao;
	
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
		
		String[] sequences = {"users_id_seq", "trips_id_seq", "reviews_id_seq", "trips_users_id_seq"}; 
		
		for (String sequence : sequences) {
		    jdbcTemplate.execute("DROP SEQUENCE " + sequence + " IF EXISTS");
		    jdbcTemplate.execute("CREATE SEQUENCE " + sequence + " as INTEGER");
		}
		
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
	}
	
	private void createTrip() {
		Trip trip = testTrip;
		User user = userDao.getById(1);
		tripDao.create(trip, user);
	}
	
	private void reserveTrip() {
		Trip trip = testTrip;
		User user = userDao.getById(1);
		
		// Create trip
		tripDao.create(trip, user);
		
		// Reserve trip
		tripDao.reserveTrip(TestUtils.TripUtils.ID, user);
		
		// Get trip
		tripDao.findById(TestUtils.TripUtils.ID);
	}
	
	@Test
	@Transactional
	public void testCreate() {
		Trip trip = testTrip;
		User user = userDao.getById(1);

		// Create trip
		final Trip createdTrip = tripDao.create(trip, user);
		
		// Asserts for trip
		assertTrip(createdTrip);
	}
	
	@Test
	@Transactional
	public void testReserve() {
		Trip trip = testTrip;
		User user = userDao.getById(1);
		
		// Create trip
		tripDao.create(trip, user);
		
		// Reserve trip
		tripDao.reserveTrip(1, user);
		
		// Get trip
		Trip reservedTrip = tripDao.findById(1);
		
		// Assert trip
		assertFalse(reservedTrip.getPassengers().contains(user));
	}
	
	@Test
	@Transactional
	public void testUnreserve() {
		User user = userDao.getById(1);
		
		// Reserve trip
		reserveTrip();
		
		// Unreserve trip
		tripDao.unreserveTrip(TestUtils.TripUtils.ID, user);
		
		// Get trip
		Trip reservedTrip = tripDao.findById(TestUtils.TripUtils.ID);
		
		// Assert trip
		assertFalse(reservedTrip.getReserved());
	}
	
	@Test
	@Transactional
	public void testDelete() {
		User user = userDao.getById(1);
		
		// Create trip
		createTrip();
		
		// Delete trip
		tripDao.delete(TestUtils.TripUtils.ID, user);
		
		// Get trip
		Trip reservedTrip = tripDao.findById(TestUtils.TripUtils.ID);
		
		// Assert trip
		assertTrue(reservedTrip.getDeleted());
	}
}