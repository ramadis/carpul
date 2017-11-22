package ar.edu.itba.paw.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserDaoJdbc;

import static org.junit.Assert.*;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.sql.DataSource;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class UserDaoHibernateTest {
	@Autowired
	private DataSource ds;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private UserDaoHibernate userDao;
	private User testUser;
	
	@Before
	public void setUp() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaDelete<User> query = builder.createCriteriaDelete(User.class);
		query.from(User.class);
		em.createQuery(query).executeUpdate();
		testUser = TestUtils.UserUtils.sampleUser();
	}
	
	public void assertUser(User user) {
		assertNotNull(user);
		assertEquals(TestUtils.UserUtils.USERNAME, user.getUsername());
		assertEquals(TestUtils.UserUtils.PASSWORD, user.getPassword());
		assertEquals(TestUtils.UserUtils.FIRST_NAME, user.getFirst_name());
		assertEquals(TestUtils.UserUtils.LAST_NAME, user.getLast_name());
		assertEquals(TestUtils.UserUtils.PHONE_NUMBER, user.getPhone_number());
		assertNotNull(user.getCreated());
		//assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
	}
	
	@Test
	public void testCreate() {
		User user = testUser;
		
		// Register user
		final User registeredUser = userDao.create(user);
		
		// Asserts
		assertUser(registeredUser);
	}
	
	@Test
	public void testGetByUsername() {
		// Create user
		testCreate();
		
		// Get user by username
		final User user = userDao.getByUsername(TestUtils.UserUtils.USERNAME);
		
		// Asserts
		assertUser(user);
	}
	
	@Test
	public void testGetById() {
		// Create user
		testCreate();
				
		// Get user by id
		final User user = userDao.getById(TestUtils.UserUtils.ID);
		
		// Asserts
		assertUser(user);
	}
}