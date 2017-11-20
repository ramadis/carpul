package ar.edu.itba.paw.webapp.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.SearchForm;

@Controller
public class SearchController extends AuthController {

	@Autowired
	private TripService ts;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchAllView(@RequestParam("from") String from,
									 @RequestParam("to") String to,
									 @RequestParam("when") Long when) {
		// Create a valid search model.
		Search search = new Search();
		search.setFrom(from.split(",")[0]);
		search.setTo(to.split(",")[0]);
		search.setWhen(when);
		
		// Get logged user
		User user = user();

		// Get trips to this search
		List<Trip> trips = user == null ? ts.findByRoute(search) : ts.findByRoute(user, search);
		List<Trip> later_trips = user == null ? ts.findAfterDateByRoute(search) : ts.findAfterDateByRoute(user, search);

		// Expose view
		final ModelAndView mav = new ModelAndView("search/search");
		mav.addObject("trips", trips);
		mav.addObject("later_trips", later_trips);
		mav.addObject("search", search);
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/search/{tripId}", method = RequestMethod.GET)
	public ModelAndView searchAllView(@PathVariable("tripId") final Integer tripId,
									 @RequestParam("from") String from,
									 @RequestParam("to") String to,
									 @RequestParam("when") Timestamp when) {
		// Get trip by id
		List<Trip> trips = new ArrayList<>();
		trips.add(ts.findById(tripId));

		// Expose view
		final ModelAndView mav = new ModelAndView("trips/individual");
		mav.addObject("trips", trips);
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
}
