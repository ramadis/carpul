package ar.edu.itba.paw.webapp.controllers;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.HistoryService;
import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.Provider;
import ar.edu.itba.paw.webapp.forms.UserCreateForm;
import ar.edu.itba.paw.webapp.forms.UserLoginForm;

import java.util.List;

@Path("users")
@Component
public class UserController {

	@Autowired
	private UserService us;
	
	@Context
	private UriInfo uriInfo;
//
//	@Autowired
//	private TripService ts;
//
//	@Autowired
//	private Provider userAuthProvider;
//
//	@Autowired
//	private ReviewService rs;
//
//	@Autowired
//	private EmailService es;
//
//	@Autowired
//	private HistoryService hs;

	@GET
	@Path("/")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response listUsers() {
		User user = new User();
		user.setUsername("testteest");
		return Response.ok(user).build();
	}
	
	@POST
	@Path("/")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response createUser(final User userDto) {
//		// Check for form errors
//		if (errors.hasErrors()) return registerUserView(form);

		// Get user from form
//		User user = form.getUser();
		final User user = us.register(userDto);

		// Check if user exists
//		if (us.exists(user)) {
//			ObjectError error = new ObjectError("username","An account already exists for this username");
//			errors.addError(error);
//			return registerUserView(form);
//		}

		// Register new user
//		us.register(user);

		// Send welcome email to user
//		es.sendRegistrationEmail(user);

		// Login automatically
//		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//		userAuthProvider.authenticate(auth);
//		SecurityContextHolder.getContext().setAuthentication(auth);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getId())).build()).build();
	}

//
//	@RequestMapping(value = "/user", method = RequestMethod.POST)
//
//	@Transactional
//	public ModelAndView registerUser(@Valid @ModelAttribute("userCreateForm") final UserCreateForm form,
//							  		final BindingResult errors) {
//		// Check for form errors
//		if (errors.hasErrors()) return registerUserView(form);
//
//		// Get user from form
//		User user = form.getUser();
//
//		// Check if user exists
//		if (us.exists(user)) {
//			ObjectError error = new ObjectError("username","An account already exists for this username");
//			errors.addError(error);
//			return registerUserView(form);
//		}
//
//		// Register new user
//		us.register(user);
//
//		// Send welcome email to user
//		es.sendRegistrationEmail(user);
//
//		// Login automatically
//		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//		userAuthProvider.authenticate(auth);
//		SecurityContextHolder.getContext().setAuthentication(auth);
//
//		// Redirect to login view
//		return new ModelAndView("redirect:/");
//	}
//
//	@RequestMapping(value = "/user", method = RequestMethod.GET)
//	public ModelAndView registerUserView(@ModelAttribute("userCreateForm") final UserCreateForm form) {
//
//		// Expose view
//		final ModelAndView mav = new ModelAndView("user/register");
//		mav.addObject("registerUserURI", "user");
//		mav.addObject("loginUserURI", "login");
//		return mav;
//	}
//
//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public ModelAndView loginView(@Valid @ModelAttribute("userForm") final UserLoginForm form) {
//
//		// Expose view
//		final ModelAndView mav = new ModelAndView("user/login");
//		mav.addObject("loginUserURI", "login");
//		mav.addObject("registerUserURI", "user");
//		return mav;
//	}
//
//	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
//	public ModelAndView getUserView(@PathVariable("userId") final Integer userId) {
//		final ModelAndView mav = new ModelAndView("user/profile");
//		User user = us.getById(userId);
//		User loggedUser = user();
//
//		// If the user does not exist
//		if (user.getId() == null) return new ModelAndView("redirect:/error/404");
//
//		// Handle profile != to loggedUser
//		if (loggedUser == null || loggedUser.getId() != userId) {
//			final ModelAndView mav_other = new ModelAndView("unauth/profile");
//			mav_other.addObject("reviews", rs.getReviews(user));
//			mav_other.addObject("trips", ts.getUserTrips(user));
//
//			user.setId(loggedUser == null ? null : loggedUser.getId());
//			mav_other.addObject("user", user);
//			return mav_other;
//		}
//
//		// Load objects to view
//		mav.addObject("trips", ts.getUserTrips(loggedUser));
//		mav.addObject("reviews", rs.getReviews(user));
//		mav.addObject("reservations", ts.getReservedTrips(loggedUser));
//		mav.addObject("histories", hs.getHistories(loggedUser));
//		mav.addObject("user", loggedUser);
//		return mav;
//	}
}
