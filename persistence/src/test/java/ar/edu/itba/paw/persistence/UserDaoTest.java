package ar.edu.itba.paw.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.User;

import static org.junit.Assert.*;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
public class UserDaoTest {
	@Autowired
	private DataSource ds;
	
	@Autowired
	private UserDao userDao;
	private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager em;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
	}
	
	@Test
	public void testCreate() {
        final User user = userDao.create(TestUtils.UserUtils.sampleUser());

		// Asserts
		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals(TestUtils.UserUtils.USERNAME, user.getUsername());
		assertEquals(TestUtils.UserUtils.FIRST_NAME, user.getFirst_name());
		assertEquals(TestUtils.UserUtils.LAST_NAME, user.getLast_name());
		assertEquals(TestUtils.UserUtils.PHONE_NUMBER, user.getPhone_number());
		assertNotNull(user.getCreated());
	}
	
	@Test
	public void testGetByUsername() {
		// Get user by username
		final User user = userDao.getByUsername(TestUtils.UserUtils.EXISTING_USERNAME);
		
		// Asserts
		assertNotNull(user);
		assertEquals(TestUtils.UserUtils.EXISTING_ID, user.getId());
		assertEquals(TestUtils.UserUtils.EXISTING_USERNAME, user.getUsername());
		assertEquals(TestUtils.UserUtils.FIRST_NAME, user.getFirst_name());
		assertEquals(TestUtils.UserUtils.LAST_NAME, user.getLast_name());
		assertEquals(TestUtils.UserUtils.PHONE_NUMBER, user.getPhone_number());
	}
	
	@Test
	public void testGetByInvalidUsername() {
		// Get user by username
		final User user = userDao.getByUsername(TestUtils.UserUtils.NONEXISTING_USERNAME);
		
		// Asserts
		assertNull(user);
	}
		
	@Test
	public void testGetById() {
		// Get user by id
		final User user = userDao.getById(TestUtils.UserUtils.EXISTING_ID);
		
		// Asserts
		assertNotNull(user);
		assertEquals(TestUtils.UserUtils.EXISTING_ID, user.getId());
		assertEquals(TestUtils.UserUtils.EXISTING_USERNAME, user.getUsername());
		assertEquals(TestUtils.UserUtils.FIRST_NAME, user.getFirst_name());
		assertEquals(TestUtils.UserUtils.LAST_NAME, user.getLast_name());
		assertEquals(TestUtils.UserUtils.PHONE_NUMBER, user.getPhone_number());
	}
	
	@Test
	public void testGetByInvalidId() {
		// Get user by id
		final User user = userDao.getById(TestUtils.UserUtils.NONEXISTING_ID);
		
		// Asserts
		assertNull(user);
	}
}