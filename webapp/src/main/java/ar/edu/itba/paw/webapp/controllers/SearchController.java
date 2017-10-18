package ar.edu.itba.paw.webapp.controllers;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.itba.paw.interfaces.HistoryService;
import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.SearchForm;
import ar.edu.itba.paw.webapp.forms.UserCreateForm;

@Controller
public class SearchController extends AuthController {

	@Autowired
	private TripService ts;
	
	@Autowired
	private HistoryService hs;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchAllView(@RequestParam("from") String from,
									 @RequestParam("to") String to,
									 @RequestParam("when") Long when) {
		
		// Create a valid search model.
		Search search = new Search();
		search.setFrom(from);
		search.setTo(to);
		search.setWhen(when);
		
		// Get trips to this search
		List<Trip> trips = ts.findByRoute(user(), search);

		// Expose view
		final ModelAndView mav = new ModelAndView("search/search");
		mav.addObject("trips", trips);
		mav.addObject("from", from);
		mav.addObject("to", to);
		mav.addObject("user", user());
		return mav;
	}
	
	@RequestMapping(value = "/search/{tripId}", method = RequestMethod.GET)
	public ModelAndView searchAllView(@PathVariable("tripId") final Integer tripId,
									 @RequestParam("from") String from,
									 @RequestParam("to") String to,
									 @RequestParam("when") Timestamp when) {
		
		List<Trip> trips = new ArrayList<>();
		trips.add(ts.findById(tripId));

		final ModelAndView mav = new ModelAndView("search/search");
		mav.addObject("trips", trips);
		mav.addObject("user", user());
		mav.addObject("from", from);
		mav.addObject("to", to);
		mav.addObject("is_searching", true);
		return mav;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchRedirect(@Valid @ModelAttribute("searchForm") final SearchForm form,
								BindingResult errors) {
		// Check for errors
		if (errors.hasErrors()) return new ModelAndView("home/index");
		
		// Compose search URI
		Search search = form.getSearch();
		return new ModelAndView("redirect:search?from=" + search.getFrom() + "&to=" + search.getTo() + "&when=" + search.getWhen().getTime());
	}
	
	// TODO: Move to TripController
	@RequestMapping(value = "/reserve/{tripId}", method = RequestMethod.POST)
	public String reserveTrip(@PathVariable("tripId") final Integer tripId) {
		
		User loggedUser = user();
		ts.reserve(tripId, loggedUser);
		
		hs.addHistory(loggedUser, tripId, "RESERVE");
		return "redirect:/user/" + loggedUser.getId();
	}
	
	// TODO: Move to TripController
	@RequestMapping(value = "/unreserve/{tripId}", method = RequestMethod.POST)
	public String unreserveTrip(@PathVariable("tripId") final Integer tripId) {
		User loggedUser = user();
		ts.unreserve(tripId, loggedUser);
		hs.addHistory(loggedUser, tripId, "UNRESERVE");
		return "redirect:/user/" + loggedUser.getId();
	}
}
