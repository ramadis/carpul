package ar.edu.itba.paw.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.paw.models.History;
import ar.edu.itba.paw.persistence.UserDaoJdbc;

import static org.junit.Assert.*;

import java.util.List;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class HistoryDaoJdbcTest {
	@Autowired
	private DataSource ds;
	
	@Autowired
	private HistoryDaoJdbc historyDao;
	
	@Autowired
	private UserDaoJdbc userDao;
	
	@Autowired
	private TripDaoJdbc tripDao;
	
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
		userDao.create(TestUtils.UserUtils.sampleUser());
		tripDao.create(TestUtils.TripUtils.sampleTrip(), TestUtils.UserUtils.sampleUser());
	}
	
	public void assertHistory(History history) {
		assertNotNull(history);
		assertEquals(TestUtils.HistoryUtils.USER_ID, history.getRelated().getId());
		assertEquals(TestUtils.HistoryUtils.TYPE, history.getType());
		assertEquals(TestUtils.HistoryUtils.TRIP_ID, history.getTrip().getId());
		assertNotNull(history.getCreated());
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "histories"));
	}
	
	@Test
	public void testAddHistory() {
		// Create review
		History createdHistory = historyDao.addHistory(TestUtils.UserUtils.sampleUser(), TestUtils.TripUtils.ID, TestUtils.HistoryUtils.TYPE);
		
		// Asserts for review
		assertHistory(createdHistory);
	}
	
	@Test
	public void testGetHistories() {
		// Create review
		historyDao.addHistory(TestUtils.UserUtils.sampleUser(), TestUtils.TripUtils.ID, TestUtils.HistoryUtils.TYPE);
		
		// Get histories by user
		List<History> histories = historyDao.getHistories(TestUtils.UserUtils.sampleUser());
		
		// Asserts for review
		assertHistory(histories.get(0));
	}
}