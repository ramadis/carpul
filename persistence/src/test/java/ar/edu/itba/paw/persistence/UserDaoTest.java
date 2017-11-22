package ar.edu.itba.paw.persistence;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserDaoJdbc;

import static org.junit.Assert.*;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import javax.persistence.EntityManager;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class UserDaoTest {
	@Autowired
	private DataSource ds;
	
	@Autowired
	private UserDao userDao;
	private JdbcTemplate jdbcTemplate;
	private User testUser;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
		jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY;");
		
		String[] sequences = { "users_id_seq" };
		for (String sequence : sequences) {
		    jdbcTemplate.execute("DROP SEQUENCE " + sequence + " IF EXISTS");
		    jdbcTemplate.execute("CREATE SEQUENCE " + sequence + " as INTEGER");
		}
	}
	
	public void assertUser(User user) {
		assertNotNull(user);
		assertEquals(TestUtils.UserUtils.USERNAME, user.getUsername());
		assertEquals(TestUtils.UserUtils.FIRST_NAME, user.getFirst_name());
		assertEquals(TestUtils.UserUtils.LAST_NAME, user.getLast_name());
		assertEquals(TestUtils.UserUtils.PHONE_NUMBER, user.getPhone_number());
		assertNotNull(user.getCreated());
	}
	
	public User createUser() {
		User user = TestUtils.UserUtils.sampleUser();
		return userDao.create(user);
	}
	
	@Test
	@Transactional
	public void testCreate() {
		User user = createUser();
		
		// Asserts
		assertUser(user);
	}
	
	@Test
	@Transactional
	public void testGetByUsername() {
		// Create user
		assertUser(createUser());
		
		// Get user by username
		final User user = userDao.getByUsername(TestUtils.UserUtils.USERNAME);
		
		// Asserts
		assertUser(user);
	}
	
	@Test
	@Transactional
	public void testGetById() {
		// Create user
		createUser();
				
		// Get user by id
		final User user = userDao.getById(TestUtils.UserUtils.ID);
		
		// Asserts
		assertUser(user);
	}
}