package ar.edu.itba.paw.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController extends AuthController {

	@RequestMapping(value = "/error/{error}", method = RequestMethod.GET)
	public ModelAndView registerUserView(@PathVariable("error") Integer error) {
		// Expose view
		final ModelAndView mav = new ModelAndView("error/base");
		mav.addObject("error", error);
		return mav;
	}
}
