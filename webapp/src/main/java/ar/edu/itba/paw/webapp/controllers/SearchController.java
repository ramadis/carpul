package ar.edu.itba.paw.webapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

@Controller
public class SearchController extends AuthController {

	@Autowired
	private TripService ts;
	private UserService us;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchAllView(@RequestParam("from") String from, @RequestParam("to") String to) {
		
		List<Trip> trips = ts.findAll(user());

		final ModelAndView mav = new ModelAndView("search/search");
		mav.addObject("trips", trips);
		mav.addObject("from", from);
		mav.addObject("to", to);
		mav.addObject("user", user());
		return mav;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String searchRedirect(@ModelAttribute("searchForm") Search search,
			BindingResult result, Model model,
			final RedirectAttributes redirectAttribute) {
		return "redirect:/search?from=" + search.getFrom() + "&to=" + search.getTo();
	}
	
	@RequestMapping(value = "/reserve/{tripId}", method = RequestMethod.POST)
	public String reserveTrip(@PathVariable("tripId") final Integer tripId) {
		User loggedUser = user();
		ts.reserve(tripId, loggedUser);
		return "redirect:/user/" + loggedUser.getId();
	}
	
	@RequestMapping(value = "/unreserve/{tripId}", method = RequestMethod.POST)
	public String unreserveTrip(@PathVariable("tripId") final Integer tripId) {
		User loggedUser = user();
		ts.unreserve(tripId, loggedUser);
		return "redirect:/user/" + loggedUser.getId();
	}
}
