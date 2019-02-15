package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private UserDao userDao;
	
	@Transactional
	public User register(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userDao.create(user);
	}
	
	public User getById(Integer id) {
		return userDao.getById(id);
	}
	
	public User findById(final Integer userId) {
		return userDao.getById(userId);
	}
	
	public User getByUsername(String username) {
		return userDao.getByUsername(username);
	}
	
	public Boolean exists(User user) {
		return userDao.exists(user);
	}
	
	public List<User> getPassengers(Trip trip) {
		return userDao.getPassengers(trip);
	}
}
