package ar.edu.itba.paw.webapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.HistoryService;
import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.UserCreateForm;
import ar.edu.itba.paw.webapp.forms.UserLoginForm;

@Controller
public class UserController extends AuthController {

	@Autowired
	private UserService us;
	
	@Autowired
	private ReviewService rs;
	
	@Autowired
	private HistoryService hs;
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ModelAndView registerUser(@Valid @ModelAttribute("userCreateForm") final UserCreateForm form,
							  		final BindingResult errors) {
		// Check for form errors
		if (errors.hasErrors()) return registerUserView(form);
		
		// Register new user
		us.register(form.getUser());
		
		// Redirect to login view
		return new ModelAndView("redirect:/login");
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView registerUserView(@ModelAttribute("userCreateForm") final UserCreateForm form) {
		
		// Expose view
		final ModelAndView mav = new ModelAndView("user/register");
		mav.addObject("registerUserURI", "user");
		mav.addObject("loginUserURI", "login");
		return mav;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginView(@Valid @ModelAttribute("userForm") final UserLoginForm form) {
		
		// Expose view
		final ModelAndView mav = new ModelAndView("user/login");
		mav.addObject("loginUserURI", "login");
		mav.addObject("registerUserURI", "user");
		return mav;
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ModelAndView getUserView(@PathVariable("userId") final Integer userId) {
		final ModelAndView mav = new ModelAndView("user/profile");
		User user = us.getById(userId);
		User loggedUser = user();

		// If the user does not exist
		if (user.getId() == null) return new ModelAndView("redirect:/error/404");
		
		// Handle profile != to loggedUser
		if (loggedUser.getId() != userId) {
			final ModelAndView mav_other = new ModelAndView("unauth/profile");
			mav_other.addObject("reviews", rs.getReviews(user));
			mav_other.addObject("trips", us.getUserTrips(user));
			
			user.setId(loggedUser.getId());
			mav_other.addObject("user", user);
			return mav_other;
		}
		
		// Load objects to view
		mav.addObject("trips", us.getUserTrips(loggedUser));
		mav.addObject("reviews", rs.getReviews(user));
		mav.addObject("reservations", us.getReservedTrips(loggedUser));
		mav.addObject("histories", hs.getHistories(loggedUser));
		mav.addObject("user", loggedUser);
		return mav;
	}
}
