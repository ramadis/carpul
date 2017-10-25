package ar.edu.itba.paw.webapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.ReviewForm;

@Controller
public class ReviewController extends AuthController {

	@Autowired
	private TripService ts;

	@Autowired
	private ReviewService rs;

	@RequestMapping(value = "/review/{tripId}", method = RequestMethod.GET)
	public ModelAndView createReviewView(@ModelAttribute("reviewForm") final ReviewForm form,
										@PathVariable("tripId") final Integer tripId) {

		// 404 if user can not make a review
		Trip trip = ts.findById(tripId);
		User loggedUser = user();
		if (trip == null || trip.getId() == null || !rs.canLeaveReview(trip, loggedUser)) return new ModelAndView("redirect:/404");

		// Expose view
		ModelAndView mav = new ModelAndView("review/add");
		mav.addObject("reviewed", trip.getDriver());
		mav.addObject("trip", trip);
		mav.addObject("user", loggedUser);
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
