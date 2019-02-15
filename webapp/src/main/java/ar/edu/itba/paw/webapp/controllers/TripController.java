package ar.edu.itba.paw.webapp.controllers;

import java.net.URI;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.EventService;
import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.DTO.UserDTO;
import ar.edu.itba.paw.webapp.forms.ReviewForm;
import ar.edu.itba.paw.webapp.forms.TripCreateForm;
import ar.edu.itba.paw.webapp.forms.UserCreateForm;

@Path("trips")
@Component
public class TripController extends AuthController {

	@Autowired
	private TripService ts;
	
	@Autowired
	private UserService us;
	
	@Autowired
	private EventService es;
	
	@Context
	private UriInfo uriInfo;
	
	@Autowired
	private ReviewService rs;
	
	
	public Response getById(@PathParam("id") final int id) {
		final User user = us.getById(id);
		if (user != null) {
			return Response.ok(new UserDTO(user)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@POST
	@Path("/{id}/reviews")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addReview(final ReviewForm form, @PathVariable("id") final Integer tripId) {
		// Check for errors
		//		if (errors.hasErrors()) return createReviewView(form, tripId);
		
		// Compose review
		User loggedUser = user();
		Trip trip = ts.findById(tripId);
		Review review = form.getReview();
		review.setOwner(loggedUser);
		review.setReviewedUser(trip.getDriver());
		review.setTrip(trip);
		
		// Persist review
		Review savedReview = rs.add(review);
		
		final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(savedReview.getId())).build();
		
		return Response.created(uri).build();
		
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTrip(@PathVariable("id") final Integer tripId) {
		User loggedUser = user();
		
		// Check you have control over the trip
		Trip trip = ts.findById(tripId);
		if (!trip.getDriver().equals(loggedUser)) return Response.status(Status.FORBIDDEN).build();
		
		// Delete trip
		ts.delete(tripId, loggedUser);
		es.registerDelete(loggedUser, tripId);
		
		// Redirect to profile
		return Response.noContent().build();
	}
	
	@PUT
	@Path("/{id}/reservation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response reserveTrip(@PathVariable("id") final Integer tripId) {
		User loggedUser = user();
		
		// Reserve trip and register in log
		ts.reserve(tripId, loggedUser);
		es.registerReserve(loggedUser, tripId);

		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}/reservation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response unreserveTrip(@PathVariable("id") final Integer tripId) {
		User loggedUser = user();
		
		// Unreserve trip and  register in log
		ts.unreserve(tripId, loggedUser);
		
		// TODO: Check why this line is commented out
		//hs.addHistory(loggedUser, tripId, "UNRESERVE");
		es.registerUnreserve(loggedUser, tripId);
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("{id}/passengers/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response kickFromTrip(@PathVariable("id") final Integer tripId, 
								 @PathVariable("userid") final Integer userId) {
		// Check logged user has control over the trip
		Trip trip = ts.findById(tripId);
		User loggedUser = user();
		
		if (!trip.getDriver().equals(loggedUser)) return Response.status(Status.FORBIDDEN).build();
		
		// Find kicked user
		User user = us.findById(userId);
		
		// Unreserve trip for the kicked user
		ts.unreserve(tripId, user);
		es.registerKicked(user, tripId);
		
		return Response.noContent().build();
	}
}
