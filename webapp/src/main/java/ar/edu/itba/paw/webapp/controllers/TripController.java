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

import ar.edu.itba.paw.interfaces.EventService;
import ar.edu.itba.paw.interfaces.HistoryService;
import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.TripCreateForm;

@Controller
public class TripController extends AuthController {

	@Autowired
	private TripService ts;
	
	@Autowired
	private HistoryService hs;
	
	@Autowired
	private EventService es;
	
	@RequestMapping(value = "/trip", method = RequestMethod.GET)
	public ModelAndView createTripView(@ModelAttribute("tripForm") final TripCreateForm form) {
		// Expose view
		final ModelAndView mav = new ModelAndView("trips/add");
		return mav;
	}
	
	@RequestMapping(value = "/trip/{tripId}/delete", method = RequestMethod.POST)
	public ModelAndView deleteTrip(@PathVariable("tripId") final Integer tripId) {
		// Delete trip
		User loggedUser = user();

		ts.delete(tripId, loggedUser);
		es.registerDelete(loggedUser, tripId);
		
		// Redirect to profile
		return new ModelAndView("redirect:/user/" + loggedUser.getId());
	}
	
	@RequestMapping(value = "/trip/{tripId}/reserve", method = RequestMethod.POST)
	public ModelAndView reserveTrip(@PathVariable("tripId") final Integer tripId) {
		// Reserve trip and register in log
		User loggedUser = user();
		ts.reserve(tripId, loggedUser);
		//hs.addHistory(loggedUser, tripId, "RESERVE");
		es.registerReserve(loggedUser, tripId);
		
		// Redirect to profile
		return new ModelAndView("redirect:/user/" + loggedUser.getId());
	}
	
	@RequestMapping(value = "/trip/{tripId}/unreserve", method = RequestMethod.POST)
	public ModelAndView unreserveTrip(@PathVariable("tripId") final Integer tripId) {
		// Unreserve trip and  register in log
		User loggedUser = user();
		ts.unreserve(tripId, loggedUser);
		//hs.addHistory(loggedUser, tripId, "UNRESERVE");
		es.registerUnreserve(loggedUser, tripId);
		
		// Redirect to profile
		return new ModelAndView("redirect:/user/" + loggedUser.getId());
	}
	
	@RequestMapping(value = "/trip/{tripId}/unreserve/{userId}", method = RequestMethod.POST)
	public ModelAndView kickFromTrip(@PathVariable("tripId") final Integer tripId,
									 @PathVariable("userId") final Integer userId) {
		
		// Check you have control over the trip
		Trip trip = ts.findById(tripId);
		User loggedUser = user();
		
		if (!trip.getDriver().equals(loggedUser)) return new ModelAndView("redirect:/404");
		
		// Create wrapper user
		User user = new User();
		user.setId(userId);
		
		// Unreserve trip for the kicked user
		ts.unreserve(tripId, user);
		// TODO: Check how to alert this change
		// hs.addHistory(loggedUser, tripId, "UNRESERVE");
		// es.registerUnreserve(loggedUser, tripId);
		
		// Redirect to profile
		return new ModelAndView("redirect:/user/" + loggedUser.getId());
	}
	
	
	@RequestMapping(value = "/trip", method = RequestMethod.POST)
	public ModelAndView createTrip(@Valid @ModelAttribute("tripForm") final TripCreateForm form,
			  					  final BindingResult errors) {
		// Check for form errors
		if (errors.hasErrors()) return createTripView(form);
		
		// Register new user
		User loggedUser = user();
		ts.register(form.getTrip(), loggedUser);
		
		// Redirect to profile view
		return new ModelAndView("redirect:/user/" + loggedUser.getId());
	}
}
