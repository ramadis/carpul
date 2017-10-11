package ar.edu.itba.paw.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

@Controller
public class TripController extends AuthController {

	@Autowired
	private TripService us;
	
	final static Logger logger = LoggerFactory.getLogger(TripController.class);
	
	@RequestMapping(value = "/trip", method = RequestMethod.GET)
	public ModelAndView createTripView(Model model) {
		Trip trip = new Trip();
		User loggedUser = user();
		model.addAttribute("user", loggedUser);
		model.addAttribute("tripForm", trip);
		final ModelAndView mav = new ModelAndView("trips/trips");
		return mav;
	}
	
	@RequestMapping(value = "delete/trip/{tripId}", method = RequestMethod.POST)
	public String deleteTrip(@PathVariable("tripId") final Integer tripId) {
		User loggedUser = user();
		us.delete(tripId, loggedUser);
		return "redirect:/user/" + loggedUser.getId();
	}
	
	
	@RequestMapping(value = "/trip", method = RequestMethod.POST)
	public String createTrip(@ModelAttribute("tripForm") Trip trip,
			BindingResult result, Model model,
			final RedirectAttributes redirectAttribute) {
		User loggedUser = user();
		us.register(trip, loggedUser);
		return "redirect:/user/" + loggedUser.getId();
	}
}
