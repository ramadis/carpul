package ar.edu.itba.paw.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;

@Controller
public class ErrorController extends AuthController {

	final static Logger logger = LoggerFactory.getLogger(ErrorController.class);
	
	@RequestMapping(value = "/error/{error}", method = RequestMethod.GET)
	public ModelAndView registerUserView(@PathVariable("error") Integer error) {
		final ModelAndView mav = new ModelAndView("error/base");
		mav.addObject("error", error);
		return mav;
	}
}
