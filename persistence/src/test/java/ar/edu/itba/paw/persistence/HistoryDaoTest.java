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

import ar.edu.itba.paw.interfaces.HistoryDao;
import ar.edu.itba.paw.interfaces.TripDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.History;

import static org.junit.Assert.*;

import java.util.List;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class HistoryDaoTest {
	@Autowired
	private DataSource ds;
	
	@Autowired
	private HistoryDao historyDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TripDao tripDao;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "trips");
		jdbcTemplate.execute("TRUNCATE TABLE trips RESTART IDENTITY;");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
		jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY;");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "histories");
		jdbcTemplate.execute("TRUNCATE TABLE histories RESTART IDENTITY;");
		
		String[] sequences = {"users_id_seq", "trips_id_seq", "histories_id_seq"}; 
		
		for (String sequence : sequences) {
		    jdbcTemplate.execute("DROP SEQUENCE " + sequence + " IF EXISTS");
		    jdbcTemplate.execute("CREATE SEQUENCE " + sequence + " as INTEGER");
		}
		
		userDao.create(TestUtils.UserUtils.sampleUser());
		tripDao.create(TestUtils.TripUtils.sampleTrip(), userDao.getById(1));
	}
	
	public void assertHistory(History history) {
		assertNotNull(history);
		assertEquals(TestUtils.HistoryUtils.USER_ID, history.getRelated().getId());
		assertEquals(TestUtils.HistoryUtils.TYPE, history.getType());
		assertEquals(TestUtils.HistoryUtils.TRIP_ID, history.getTrip().getId());
		assertNotNull(history.getCreated());
	}
	
	public History createHistory() {
		History history = historyDao.addHistory(userDao.getById(1), TestUtils.TripUtils.ID, TestUtils.HistoryUtils.TYPE, false);
		return history;
	}
	
	@Test
	@Transactional
	public void testAddHistory() {
		// Create review
		History createdHistory = createHistory();
		
		// Asserts for review
		assertHistory(createdHistory);
	}
	
	@Test
	@Transactional
	public void testGetHistories() {
		// Create review
		createHistory();
		
		// Get histories by user
		List<History> histories = historyDao.getHistories(userDao.getById(1));
		
		// Asserts for review
		assertHistory(histories.get(0));
	}
}