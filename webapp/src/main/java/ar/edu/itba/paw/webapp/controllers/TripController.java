package ar.edu.itba.paw.webapp.controllers;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Path("trips")
@Component
public class TripController extends AuthController {
    private final static Logger console = LoggerFactory.getLogger(TripController.class);

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
	
	@Autowired
    private Validator validator;
	
	
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addReview(final ReviewForm form, @PathParam("id") final Integer tripId) {
		// Validate review form is schema-compliant
		if (!validator.validate(form).isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		// Check requirements for review are passed
		User loggedUser = user();
		Trip trip = ts.findById(tripId);
		if (loggedUser == null || trip == null) return Response.status(Status.NOT_FOUND).build();
		boolean wasPassenger = trip.getPassengers().stream().anyMatch(p -> p.getId().equals(loggedUser.getId()));
		if(!wasPassenger) return Response.status(Status.UNAUTHORIZED).build();
		
		// Compose review
		Review review = form.getReview();
		review.setOwner(loggedUser);
		review.setReviewedUser(trip.getDriver());
		review.setTrip(trip);
		
		// Persist review
		Review savedReview = rs.add(review);
		
		// TODO: Change this uri returned on creation
		final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(savedReview.getId())).build();
		
		return Response.created(uri).build();
		
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	// TODO: This endpoint is working
	public Response deleteTrip(@PathParam("id") final Integer tripId) {
		User loggedUser = user();
		
		// Check you have control over the trip
		Trip trip = ts.findById(tripId);
		if (trip == null || trip.getDeleted() || loggedUser == null) return Response.status(Status.NOT_FOUND).build();
		if (!trip.getDriver().equals(loggedUser)) return Response.status(Status.UNAUTHORIZED).build();
		
		// Delete trip
		ts.delete(tripId, loggedUser);
		es.registerDelete(loggedUser, tripId);
		
		// Return success
		return Response.noContent().build();
	}
	
	@PUT
	@Path("/{id}/reservation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response reserveTrip(@PathParam("id") final Integer tripId) {
		User loggedUser = user();
		
		// Check that trip exists
		Trip dbTrip = ts.findById(tripId);
		if (dbTrip == null || loggedUser == null) return Response.status(Status.NOT_FOUND).build();
		
		// Reserve trip and register in log
		ts.reserve(tripId, loggedUser);
		es.registerReserve(loggedUser, tripId);

		// Return success
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}/reservation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response unreserveTrip(@PathParam("id") final Integer tripId) {
		User loggedUser = user();
		
		// Check that trip exists
		Trip dbTrip = ts.findById(tripId);
		if (dbTrip == null || loggedUser == null) return Response.status(Status.NOT_FOUND).build();
		
		// Unreserve trip and notify
		ts.unreserve(tripId, loggedUser);
		es.registerUnreserve(loggedUser, tripId);
		
		// Return success
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("{id}/passengers/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response kickFromTrip(@PathParam("id") final Integer tripId, 
								 @PathParam("userid") final Integer userId) {
		// Check logged user is authorized to edit the trip
		Trip trip = ts.findById(tripId);
		User loggedUser = user();
		if (trip == null) return Response.status(Status.NOT_FOUND).build();
		if (loggedUser == null || !trip.getDriver().equals(loggedUser)) return Response.status(Status.UNAUTHORIZED).build();
		
		// Find kicked user
		User user = us.findById(userId);
		if (user == null) return Response.status(Status.NOT_FOUND).build();
		
		// Unreserve trip for the kicked user
		ts.unreserve(tripId, user);
		es.registerKicked(user, tripId);
		
		// Return success
		return Response.noContent().build();
	}
}
