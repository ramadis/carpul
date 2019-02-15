package ar.edu.itba.paw.webapp.controllers;

import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.HistoryService;
import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.History;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.webapp.DTO.HistoryDTO;
import ar.edu.itba.paw.webapp.DTO.ReviewDTO;
import ar.edu.itba.paw.webapp.DTO.TripDTO;
import ar.edu.itba.paw.webapp.DTO.UserDTO;
import ar.edu.itba.paw.webapp.forms.TripCreateForm;
import ar.edu.itba.paw.webapp.forms.UserCreateForm;
import ar.edu.itba.paw.models.User;

import java.util.ArrayList;
import java.util.List;

@Path("users")
@Component
public class UserController extends AuthController {
	private final static Logger console = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService us;
	
	@Context
	private UriInfo uriInfo;

	@Autowired
	private TripService ts;

	@Autowired
	private ReviewService rs;

	@Autowired
	private EmailService es;

	@Autowired
	private HistoryService hs;
	
	@Autowired
    private Validator validator;

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// TODO: This endpoint is working
	public Response createUser(final UserCreateForm form) {
		// Check if the user form is valid
		if (!validator.validate(form).isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		// Get user from form
		User user = form.getUser();
		
		// Check if user exists
		if (us.exists(user)) {
			return Response.status(Status.CONFLICT).build();
		}

		// Register new user
		us.register(user);

		// Send welcome email to user
		es.sendRegistrationEmail(user);
		
		final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getId())).build();

		return Response.created(uri).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	// TODO: This endpoint is working
	public Response getById(@PathParam("id") final int id) {
		final User user = us.getById(id);
		
		if (user != null) {
			return Response.ok(new UserDTO(user)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@POST
	@Path("/{id}/trips")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// TODO: This endpoint is working
	public Response createTrip(final TripCreateForm form, @PathParam("id") final int id) {
		// Check if the trip form is valid
		if (!validator.validate(form).isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		User loggedUser = user();

		// If trying to create a trip for someone else, fail by unauthorized
		if (!loggedUser.getId().equals(id)) return Response.status(Status.UNAUTHORIZED).build();
			
		// Create trip with logged user as a driver
		Trip trip = ts.register(form.getTrip(), loggedUser);
		
		final URI uri = uriInfo.getBaseUriBuilder().path("trips").path(String.valueOf(trip.getId())).build();
		return Response.created(uri).build();
	}
	
	@GET
	@Path("/{id}/trips")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOwnTrips(@PathParam("id") final int id,
								@DefaultValue("0") @QueryParam("page") int page,
								@DefaultValue("25") @QueryParam("per_page") int perPage) {
		final User user = us.getById(id);
		if (user == null) return Response.status(Status.NOT_FOUND).build();
		
		// Search trips belonging to a given user
		List<TripDTO> tripDTOs = new ArrayList<>();
		List<Trip> trips = ts.getUserTrips(user);

		if (trips == null || trips.isEmpty()) return Response.noContent().build();
		
		// Return trips owned by the user with the param id
		for (Trip t: trips) tripDTOs.add(new TripDTO(t));
		return Response.ok(tripDTOs).build();
	}
	
	@GET
	@Path("/{id}/reviews")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReviews(@PathParam("id") final int id) {
		final User user = us.getById(id);
		if (user != null) return Response.status(Status.NOT_FOUND).build();
		
		// Transform reviews to DTOs
		List<ReviewDTO> reviewDTOs = new ArrayList<>();
		List<Review> reviews = rs.getReviews(user);
		if (reviews == null || reviews.isEmpty()) return Response.status(Status.NOT_FOUND).build();
		
		// Return reviews for a given user id
		for (Review r: reviews) reviewDTOs.add(new ReviewDTO(r));
		return Response.ok(reviewDTOs).build();
	}
	
	@GET
	@Path("/{id}/history")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHistory(@PathParam("id") final int id) {
		final User user = us.getById(id);
		if (user != null) Response.status(Status.NOT_FOUND).build();
		
		List<HistoryDTO> historyDTOs = new ArrayList<>();
		List<History> histories = hs.getHistories(user);
		if (histories == null || histories.isEmpty()) return Response.status(Status.NOT_FOUND).build();

		
		for (History h: histories) historyDTOs.add(new HistoryDTO(h));
		return Response.ok(histories).build();
	}
	
	@GET
	@Path("/{id}/reservations")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReservations(@PathParam("id") final int id) {
		// TODO: Check permissions and what to do with default values for pagination
		final User user = us.getById(id);
		if (user == null) return Response.status(Status.NOT_FOUND).build();
		
		// Search trips belonging to a given user
		List<TripDTO> tripDTOs = new ArrayList<>();
		List<Trip> trips = ts.getReservedTrips(user);

		if (trips == null || trips.isEmpty()) return Response.noContent().build();
		
		// Return trips owned by the user with the param id
		for (Trip t: trips) tripDTOs.add(new TripDTO(t));
		return Response.ok(tripDTOs).build();
	}
}
