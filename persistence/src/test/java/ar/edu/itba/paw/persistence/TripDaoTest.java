package ar.edu.itba.paw.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.TripDao;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
public class TripDaoTest {
	@Autowired
	private DataSource ds;
	
	@Autowired
	private TripDao tripDao;
	
	private JdbcTemplate jdbcTemplate;
	
	@PersistenceContext
	private EntityManager em;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
	}
	
	@Test
	public void testCreate() {
		// Create helper objects
		Trip trip = TestUtils.TripUtils.sampleTrip();
		User user = new User();
		user.setId(TestUtils.UserUtils.EXISTING_ID);

		// Create trip
		trip = tripDao.create(trip, user);
		
		// Asserts for trip
		assertNotNull(trip);
		assertNotNull(trip.getId());
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
	
	@Test
	public void testReserve() {
		// Create helper objects
		User user = new User();
		user.setId(TestUtils.TripUtils.NONEXISTING_PASSENGER_ID);
		int reservedTripsAmount = 1;
		
		// Reserve trip for a user
		tripDao.reserveTrip(TestUtils.TripUtils.EXISTING_ID, user);
		
		em.flush();
		
		// Get reserved trips from the database
		Object[] params = new Object[] { TestUtils.TripUtils.EXISTING_ID , user.getId() };
		List<Map<String, Object>> results = jdbcTemplate.queryForList("SELECT * FROM trips_users WHERE trip_id = ? AND user_id = ?", params);
		
		assertEquals(reservedTripsAmount, results.size());
	}
	
	@Test
	public void testUnreserve() {
		// Create helper objects
		User user = new User();
		user.setId(TestUtils.TripUtils.EXISTING_PASSENGER_ID);
		int reservedTripsAmount = 0;
		
		// Unreserve trip
		tripDao.unreserveTrip(TestUtils.TripUtils.ID, user);
		
		em.flush();
		
		// Get reserved trips from the database
		Object[] params = new Object[] { TestUtils.TripUtils.EXISTING_ID , TestUtils.UserUtils.EXISTING_ID };
		List<Map<String, Object>> results = jdbcTemplate.queryForList("SELECT * FROM trips_users WHERE trip_id = ? AND user_id = ?", params);

		
		// Assert trip
		assertEquals(reservedTripsAmount, results.size());
	}
	
	@Test
	public void testDelete() {
		// Create helper objects
		User user = new User();
		user.setId(TestUtils.TripUtils.EXISTING_DRIVER_ID);
		int tripsAmount = 0;

		// delete trip
		tripDao.delete(TestUtils.TripUtils.EXISTING_ID, user);
		
		em.flush();
		
		// Get trips from the database
		Object[] params = new Object[] { TestUtils.TripUtils.EXISTING_ID };
		List<Map<String, Object>> results = jdbcTemplate.queryForList("SELECT * FROM trips WHERE id = ? AND DELETED=FALSE", params);
		
		// Assert trip
		assertEquals(tripsAmount, results.size());
	}
}