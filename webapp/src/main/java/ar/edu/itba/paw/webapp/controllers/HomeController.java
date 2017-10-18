package ar.edu.itba.paw.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.webapp.forms.SearchForm;

@Controller
public class HomeController extends AuthController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(@ModelAttribute("searchForm") final SearchForm form) {
		// Expose view
		final ModelAndView mav = new ModelAndView("home/index");
		return mav;
	}
}
