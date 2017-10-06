package ar.edu.itba.paw.webapp.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import ar.edu.itba.paw.models.User;

public class Model extends org.springframework.security.core.userdetails.User {
	private static final long serialVersionUID = 7432253838250082238L;
	private User user;
	
	public User getUser() {
		return user;
	}
	
	public Model(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUsername(), user.getPassword(), authorities);
		this.user = user;
	}

	public Model(User user, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(user.getUsername(), user.getPassword(), enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		this.user = user;
	}

}