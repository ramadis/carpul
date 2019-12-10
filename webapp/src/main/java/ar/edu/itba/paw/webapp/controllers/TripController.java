package ar.edu.itba.paw.webapp.controllers;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

import ar.edu.itba.paw.interfaces.EventService;
import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.DTO.ErrorDTO;
import ar.edu.itba.paw.webapp.DTO.ReservationDTO;
import ar.edu.itba.paw.webapp.DTO.ReviewDTO;
import ar.edu.itba.paw.webapp.DTO.TripDTO;
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
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") final int id) {
		final Trip trip = ts.findById(id);
		final User user = user();
		if (trip != null) {
			if (trip.getDriver().equals(user)) {
				return Response.ok(new TripDTO(trip)).build();
			} else {
				return Response.ok(new ReservationDTO(trip)).build();
			}
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@POST
	@Path("/{id}/reviews")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addReview(final ReviewForm form, @PathParam("id") final Integer tripId) {
		User loggedUser = user();

		// Validate review form is schema-compliant
		if (form == null) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorDTO(Status.BAD_REQUEST.getStatusCode(), "form", "form is null")).build();
		}
		
		if (!validator.validate(form).isEmpty()) {
			List<ErrorDTO> errors = validator.validate(form).stream().map(validation -> new ErrorDTO(Status.BAD_REQUEST.getStatusCode(), validation.getPropertyPath() + "", validation.getMessage())).collect(Collectors.toList());
			return Response.status(Status.BAD_REQUEST).entity(errors).build();
		}
		
		// Check requirements for reviewing are passed
		Trip trip = ts.findById(tripId);
		if (trip == null) return Response.status(Status.NOT_FOUND).build();
		boolean wasPassenger = trip.getPassengers().contains(loggedUser);
		if(!wasPassenger) return Response.status(Status.FORBIDDEN).build();
		if (!rs.canLeaveReview(trip, loggedUser)) return Response.status(Status.CONFLICT).build();
		
		// Compose review
		Review review = form.getReview();
		review.setOwner(loggedUser);
		review.setReviewedUser(trip.getDriver());
		review.setTrip(trip);
		
		// Persist review
		Review savedReview = rs.add(review);
		
		console.info("Review created successfully.");

		// Return new review with its id
		final URI uri = uriInfo.getBaseUriBuilder().path("/reviews/{id}").build(savedReview.getId());
		return Response.created(uri).entity(new ReviewDTO(savedReview)).build();
		
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	// TODO: This endpoint is working
	public Response deleteTrip(@PathParam("id") final Integer tripId) {
		User loggedUser = user();
		
		// Check you have control over the trip
		Trip trip = ts.findById(tripId);
		if (trip == null) return Response.status(Status.NOT_FOUND).build();
		if (!trip.getDriver().equals(loggedUser)) return Response.status(Status.FORBIDDEN).build();
		if (trip.getDeleted())return Response.noContent().build();
		
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
		Trip trip = ts.findById(tripId);
		if (trip == null) return Response.status(Status.NOT_FOUND).build();
		
		// Make several checks to ensure the user can reserve the trip
		Date date = new Date();
		Timestamp now = new Timestamp(date.getTime());
		
		boolean isLate = now.after(trip.getEtd());
		boolean isFull = trip.getAvailable_seats() < 1;
		boolean isDriver = trip.getDriver().equals(loggedUser);
		boolean hasReserved = us.getPassengers(trip).contains(loggedUser);
		boolean isOverlapping = ts.areTimeConflicts(trip, loggedUser);
		
		if (isLate || isFull || isDriver || isOverlapping) return Response.status(Status.CONFLICT).build();
		if (hasReserved) return Response.noContent().build();
		
		// Reserve and register trip 
		ts.reserve(tripId, loggedUser);
		es.registerReserve(loggedUser, tripId);

		// Return success
		return Response.status(Status.CREATED).build();
	}
	
	@DELETE
	@Path("/{id}/reservation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response unreserveTrip(@PathParam("id") final Integer tripId) {
		User loggedUser = user();
		
		// Check that trip exists
		Trip trip = ts.findById(tripId);
		if (trip == null) return Response.status(Status.NOT_FOUND).build();
		
		// Check that has permissions required
		if (!trip.getPassengers().contains(loggedUser)) Response.noContent().build();

		// Check if it's too late
		Date date = new Date();
		Timestamp now = new Timestamp(date.getTime());
		boolean isLate = now.after(trip.getEtd());
		if (isLate) return Response.status(Status.FORBIDDEN).build();
		
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
		if (!trip.getDriver().equals(loggedUser)) return Response.status(Status.FORBIDDEN).build();
		
		// Find kicked user
		User user = us.findById(userId);
		if (user == null) return Response.status(Status.NOT_FOUND).build();
		
		// Check if it's too late
		Date date = new Date();
		Timestamp now = new Timestamp(date.getTime());
		boolean isLate = now.after(trip.getEtd());
		if (isLate) return Response.status(Status.FORBIDDEN).build();
		
		// Unreserve trip for the kicked user
		ts.unreserve(tripId, user);
		es.registerKicked(user, tripId);
		
		// Return success
		return Response.noContent().build();
	}
}
