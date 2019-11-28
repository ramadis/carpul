package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDaoHibernate implements UserDao {

	@PersistenceContext
	private EntityManager em;

	public static void loadResultIntoUser(ResultSet rs, User user) {
		try {
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setFirst_name(rs.getString("first_name"));
			user.setCreated(rs.getTimestamp("created"));
			user.setLast_name(rs.getString("last_name"));
			user.setPhone_number(rs.getString("phone_number"));
			user.setId(rs.getInt("id"));
		} catch (Exception e) {
		}
	}
	
	public User update(User toUpdate, User user) {
		if (user.getFirst_name() != null) toUpdate.setFirst_name(user.getFirst_name());
		if (user.getLast_name() != null) toUpdate.setLast_name(user.getLast_name());
		if (user.getPhone_number() != null) toUpdate.setPhone_number(user.getPhone_number());
		em.merge(toUpdate);
		return toUpdate;
	}
	
	public User uploadProfileImage(User user, byte[] image) {
		user.setProfileImage(image);
		em.merge(user);
		return user;
	}
	
	public User uploadCoverImage(User user, byte[] image) {
		user.setCoverImage(image);
		em.merge(user);
		return user;
	}

	public static void loadReducedResultIntoUser(ResultSet rs, User user) {
		try {
			user.setFirst_name(rs.getString("first_name"));
			user.setLast_name(rs.getString("last_name"));
			user.setPhone_number(rs.getString("phone_number"));
		} catch (Exception e) {}
	}
	
	public Boolean exists(User user) {
		if (user == null) return false;
		
		User newUser = getByUsername(user.getUsername());
		if (newUser == null) return false;
		
		return newUser.getId() != null;
	}

	public User create(User user) {
		// Add created timestamp
		Timestamp now = new Timestamp(System.currentTimeMillis());
		user.setCreated(now);
		
		em.persist(user);

		return user;
	}

	@Override
	public User getByUsername(final String username) {
		String query = "from User as u WHERE u.username = :username";
		
		List<User> users = em.createQuery(query, User.class)
							 .setParameter("username", username)
							 .getResultList();
		
		return users.isEmpty() ? null : users.get(0);
	}

	public User getById(Integer id) {
		return findById(id);
	}
	
	@Override
	public User findById(Integer id) {
		return em.find(User.class, id);
	}

	public List<User> getPassengers(Trip trip) {
		List<User> passengers = trip.getReservations().stream().map((reservation) -> reservation.getUser()).collect(Collectors.toList());
		
		
		return passengers == null ? new ArrayList<User>() : passengers;
	}


}
