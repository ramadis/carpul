package ar.edu.itba.paw.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;

@Controller
public class UserController extends AuthController {

	@Autowired
	private UserService us;
	
	final static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView registerUserView(Model model) {
		User user = new User("asdf", "asdf");
		model.addAttribute("userForm", user);
		final ModelAndView mav = new ModelAndView("user/register");
		mav.addObject("registerUserURI", "/webapp/user");
		mav.addObject("loginUserURI", "/webapp/login");
		return mav;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginView(Model model) {
		User user = new User();
		model.addAttribute("userForm", user);
		final ModelAndView mav = new ModelAndView("login");
		mav.addObject("loginUserURI", "/login");
		mav.addObject("registerUserURI", "/user");
		return mav;
	}
	
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ModelAndView getUserView(@PathVariable("userId") final Integer userId) {
		final ModelAndView mav = new ModelAndView("user/profile");
		User loggedUser = user();
		if (loggedUser.getId() != userId) return new ModelAndView("login");
		mav.addObject("trips", us.getUserTrips(loggedUser));
		mav.addObject("reservations", us.getReservedTrips(loggedUser));
		mav.addObject("user", loggedUser);
		return mav;
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("userForm") User user,
			BindingResult result, Model model,
			final RedirectAttributes redirectAttribute) {
		us.register(user);
		User registeredUser = us.getByUsername(user.getUsername());
		return "redirect:/login";
	}
}
