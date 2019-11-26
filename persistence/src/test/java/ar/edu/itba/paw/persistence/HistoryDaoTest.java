package ar.edu.itba.paw.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.HistoryDao;
import ar.edu.itba.paw.models.History;
import ar.edu.itba.paw.models.User;

import static org.junit.Assert.*;

import java.util.List;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
public class HistoryDaoTest {
	@Autowired
	private HistoryDao historyDao;
	
	@Test
	public void testAddHistory() {
		// Create helper objects
		User user = new User();
		user.setId(TestUtils.HistoryUtils.NONEXISTING_PASSENGER_ID);
		
		// Create review
		History history = historyDao.addHistory(user, TestUtils.HistoryUtils.EXISTING_TRIP_ID, TestUtils.HistoryUtils.TYPE, false);
		
		// Asserts for review
		assertNotNull(history);
		assertNotNull(history.getId());
		assertEquals(TestUtils.HistoryUtils.USER_ID, history.getRelated().getId());
		assertEquals(TestUtils.HistoryUtils.TYPE, history.getType());
		assertEquals(TestUtils.HistoryUtils.TRIP_ID, history.getTrip().getId());
		assertNotNull(history.getCreated());
	}
	
	@Test
	public void testGetHistories() {
		// Create helper objects
		User user = new User();
		user.setId(TestUtils.HistoryUtils.EXISTING_PASSENGER_ID);
		int expectedHistoriesAmount = 1;
		
		// Get histories by user
		List<History> histories = historyDao.getHistories(user);
		
		assertEquals(expectedHistoriesAmount, histories.size());
	}
}