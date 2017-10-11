package ar.edu.itba.paw.webapp.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.Model;
import ar.edu.itba.paw.webapp.auth.Provider;
import ar.edu.itba.paw.webapp.auth.Service;

@Controller
public abstract class AuthController {
	final static Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private UserService us;
	
	@ExceptionHandler(value = Exception.class)
    public String redirectToErrorPage(){
        return "redirect:/errors";
    }
	
	@ModelAttribute
	public User user() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String username;
		System.out.println("auth instanceof Anonymous >> " + (auth instanceof AnonymousAuthenticationToken));
		if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
			return null;
		}
		final Object principal = auth.getPrincipal();
		if (principal instanceof Model) {
			return ((Model) principal).getUser();
		} else if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		try {
			return us.getByUsername(username);

		} catch (IllegalStateException e) {
			System.out.println("Not found user " + username);
			logger.debug("No username {}.", username, e);
			return null;
		}
	}
}