package ar.edu.itba.paw.webapp.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.auth.JWTFilter;
import ar.edu.itba.paw.webapp.auth.Model;
import ar.edu.itba.paw.models.User;


@Controller
public abstract class AuthController {
    private final static Logger console = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private UserService us;

	@ModelAttribute
	public User user() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String username;

		if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) return null;

		final Object principal = auth.getPrincipal();

		if (principal instanceof Model) return ((Model) principal).getUser();

		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}

		try {
			return us.getByUsername(username);
		} catch (IllegalStateException e) {
			console.error(e.getMessage());
			return null;
		}
	}
}
