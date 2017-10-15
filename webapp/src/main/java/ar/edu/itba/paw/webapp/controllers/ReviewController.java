package ar.edu.itba.paw.webapp.controllers;

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

import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

@Controller
public class ReviewController extends AuthController {

	@Autowired
	private TripService ts;
	
	@Autowired
	private ReviewService rs;
	
	@RequestMapping(value = "/review/{tripId}", method = RequestMethod.GET)
	public ModelAndView createReviewView(Model model, @PathVariable("tripId") final Integer tripId) {
		// Check if logged user can leave a review for the requested trip;
		//TODO: check for date < now
		//TODO: If jdbc returns empty, then return null.
		Trip trip = ts.findById(tripId);
		if (trip == null || trip.getId() == null) return new ModelAndView("redirect:/404");
		User loggedUser = user();
		//TODO: Add passengers when querying trip from id
		//if (!trip.getPassengerIds().contains(loggedUser.getId())) return null;
		
		// Create review model
		Review review = new Review();

		// Load model
		model.addAttribute("reviewForm", review);
		
		// Add view
		ModelAndView mav = new ModelAndView("review/add");
		mav.addObject("user", trip.getDriver());
		mav.addObject("trip", trip);
		mav.addObject("owner", loggedUser);
		
		return mav;
	}
	
	@RequestMapping(value = "/review/{tripId}", method = RequestMethod.POST)
	public ModelAndView addReview(@ModelAttribute("reviewForm") Review review,
			@PathVariable("tripId") final Integer tripId,
			BindingResult result, Model model,
			final RedirectAttributes redirectAttribute) {
		
		// Load review model
		User loggedUser = user();
		Trip trip = ts.findById(tripId);
		review.setOwner(loggedUser);
		review.setReviewedUser(trip.getDriver());
		review.setTrip(trip);
		
		System.out.println("Review:" + review.getTrip().getId());
		System.out.println("Review is being made");
		rs.add(review);
		System.out.println("Review was made");
		System.out.println(loggedUser.getId());
		return new ModelAndView("redirect:/user/" + loggedUser.getId());	
	
	}
}
