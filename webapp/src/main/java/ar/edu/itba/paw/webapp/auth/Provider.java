package ar.edu.itba.paw.webapp.auth;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;

@Component
public class Provider implements AuthenticationProvider {

	@Autowired
	private UserService us;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final String username = (String) authentication.getPrincipal();
		final String password = (String) authentication.getCredentials();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		try {
			final User user = us.getByUsername(username);
			
			// TODO: Encrypt passwords.
			// encoder.encode(password);
			if (user.getPassword().equals(password)) {
				final Collection<GrantedAuthority> authorities = new HashSet<>();
				authorities.add(new SimpleGrantedAuthority("USER"));

				return new UsernamePasswordAuthenticationToken(username, password, authorities);
			}

		} catch (IllegalStateException e) {}
		throw new UsernameNotFoundException("No user found by the name " + username);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
