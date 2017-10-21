package ar.edu.itba.paw.webapp.controllers;

import javax.validation.Valid;

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
import ar.edu.itba.paw.webapp.forms.ReviewForm;
import ar.edu.itba.paw.webapp.forms.SearchForm;

@Controller
public class ReviewController extends AuthController {

	@Autowired
	private TripService ts;
	
	@Autowired
	private ReviewService rs;
	
	@RequestMapping(value = "/review/{tripId}", method = RequestMethod.GET)
	public ModelAndView createReviewView(@ModelAttribute("reviewForm") final ReviewForm form,
										@PathVariable("tripId") final Integer tripId) {
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

		// Add view
		ModelAndView mav = new ModelAndView("review/add");
		mav.addObject("user", trip.getDriver());
		mav.addObject("trip", trip);
		mav.addObject("owner", loggedUser);
		
		return mav;
	}
	
	@RequestMapping(value = "/review/{tripId}", method = RequestMethod.POST)
	public ModelAndView addReview(@Valid @ModelAttribute("reviewForm") final ReviewForm form,
								 BindingResult errors,
								 @PathVariable("tripId") final Integer tripId) {
		// Check for errors
		if (errors.hasErrors()) return createReviewView(form, tripId);
		
		// Compose review
		User loggedUser = user();
		Trip trip = ts.findById(tripId);
		Review review = form.getReview();
		review.setOwner(loggedUser);
		review.setReviewedUser(trip.getDriver());
		review.setTrip(trip);
		
		// Persist review
		rs.add(review);

		// Redirect to profile
		return new ModelAndView("redirect:/user/" + loggedUser.getId());	
	
	}
}
