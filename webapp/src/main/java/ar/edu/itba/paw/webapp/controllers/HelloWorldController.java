package ar.edu.itba.paw.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {
	
	@RequestMapping()
	public ModelAndView helloWorld() {
		final ModelAndView mav = new ModelAndView("helloWorld");
		mav.addObject("greeting", "Hello PAW!");
		return mav;
	}
}
