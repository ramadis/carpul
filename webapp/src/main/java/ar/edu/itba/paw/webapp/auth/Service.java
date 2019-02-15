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
    private final static Logger console = LoggerFactory.getLogger(JWTLogin.class);

	@Autowired
	private UserService us;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			final User user = us.getByUsername(username);
			if (username == "" || user == null) throw new UsernameNotFoundException("No user found by the name " + username);
			
			console.info("User with username {} found successfully", username);
			final Collection<GrantedAuthority> authorities = new HashSet<>();
			
			authorities.add(new SimpleGrantedAuthority("USER"));
			return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
		} catch (IllegalStateException e) {
			console.error("No user found with username {}", username);
			throw new UsernameNotFoundException("No user found by the name " + username);
		}
	}
}