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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Trip;

@Controller
public class SearchController {

	@Autowired
	private TripService ts;
	private UserService us;
	
	@RequestMapping(value = "/search/{passengerId}", method = RequestMethod.GET)
	public ModelAndView searchView(@PathVariable("passengerId") final Integer passengerId) {
		
		List<Trip> trips = ts.findByPassenger(passengerId);
		
		trips.forEach((Trip trip) -> {
			//trip.setDriver(us.findById(trip.getDriver_id().toString()));
		});
		final ModelAndView mav = new ModelAndView("search/index");
		mav.addObject("trips", trips);
		return mav;
	}
}
