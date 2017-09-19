package ar.edu.itba.paw.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController {

	@Autowired
	private UserService us;
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView registerUserView(Model model) {
		User user = new User();
		model.addAttribute("userForm", user);
		final ModelAndView mav = new ModelAndView("userRegister");
		mav.addObject("registerUserURI", "/webapp/user");
		mav.addObject("loginUserURI", "/webapp/login");
		return mav;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginView(Model model) {
		User user = new User("asdf", "asdf");
		model.addAttribute("userForm", user);
		final ModelAndView mav = new ModelAndView("login");
		mav.addObject("loginUserURI", "/webapp/login");
		mav.addObject("registerUserURI", "/webapp/user");
		return mav;
	}
	
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ModelAndView getUserView(@PathVariable("userId") final String userId) {
		final ModelAndView mav = new ModelAndView("userProfile");
		User user = us.findById(userId);
		mav.addObject("user", user);
		return mav;
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("userForm") User user,
			BindingResult result, Model model,
			final RedirectAttributes redirectAttribute) {
		us.register(user);
		return "redirect:/user/" + user.getUsername();
	}
}
