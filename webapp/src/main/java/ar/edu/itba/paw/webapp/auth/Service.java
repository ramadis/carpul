package ar.edu.itba.paw.webapp.auth;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;

@Component
public class Service implements UserDetailsService {

	@Autowired
	UserService us;
	
	static private final Logger logger = LoggerFactory.getLogger(Service.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			System.out.println("Fetching user " + username);
			logger.debug("Fetching user {}", username);
			final User user = us.getByUsername(username);
			final Collection<GrantedAuthority> authorities = new HashSet<>();
			
			authorities.add(new SimpleGrantedAuthority("USER"));
			return new Model(user, true, true, true, true, authorities);
		} catch (IllegalStateException e) {
			System.out.println("Not found user " + username);
			throw new UsernameNotFoundException("No user found by the name " + username);
		}
	}
}