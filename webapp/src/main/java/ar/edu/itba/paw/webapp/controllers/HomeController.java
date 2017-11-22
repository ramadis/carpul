package ar.edu.itba.paw.webapp.controllers;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.SearchForm;

@Controller
public class HomeController extends AuthController {
	
	@Autowired
	private TripService ts;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(@ModelAttribute("searchForm") final SearchForm form) {
		
		User loggedUser = user();
		Search search = new Search("", "", new Timestamp(System.currentTimeMillis()));
		
		// Expose view
		final ModelAndView mav = new ModelAndView("home/index");
		
		// TODO: Should use other endpoint. Should bring only ~5 options. Should group by to_city; Should redirect to the relevan search item
		mav.addObject("trips", ts.getSuggestions(loggedUser, search));
		mav.addObject("now", System.currentTimeMillis());
		return mav;
	}
}
