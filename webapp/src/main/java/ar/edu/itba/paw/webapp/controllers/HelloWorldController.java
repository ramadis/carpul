package ar.edu.itba.paw.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.Search;

@Controller
public class HelloWorldController extends AuthController {
	@RequestMapping("/")
	public ModelAndView home(Model model) {
		Search search = new Search();
		model.addAttribute("searchForm", search);
		final ModelAndView mav = new ModelAndView("home/index");
		return mav;
	}
}
