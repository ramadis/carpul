package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public User register(final String username, final String password) {
		return userDao.create(username, password);
	}
	
	public User findById(final Integer userId) {
		return userDao.findById(userId);
	}
	
	public User getByUsername(String username) {
		return userDao.getByUsername(username);
	}
}
