package ar.edu.itba.paw.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.UserService;

@Controller
public class HelloWorldController extends AuthController {

	@Autowired
	private UserService us;
	
	@RequestMapping("/")
	public String helloWorld() {
		return "redirect:/search?from=Buenos Aires&to=Pinamar";
	}
}
