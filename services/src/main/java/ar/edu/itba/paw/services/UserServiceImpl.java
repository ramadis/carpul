package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public User register(User user) {
		return userDao.create(user);
	}
	
	public User getById(Integer id) {
		return userDao.getById(id);
	}
	
	public User findById(final Integer userId) {
		return userDao.findById(userId);
	}
	
	public User getByUsername(String username) {
		return userDao.getByUsername(username);
	}
	
	public List<Trip> getUserTrips(User user) {
		return userDao.getUserTrips(user);
	}
	
	public List<Trip> getReservedTrips(User user) {
		return userDao.getReservedTrips(user);
	}
}
