package ar.edu.itba.paw.webapp.controllers;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.HistoryService;
import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.History;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.DTO.UserDTO;
import ar.edu.itba.paw.webapp.auth.Provider;
import ar.edu.itba.paw.webapp.forms.TripCreateForm;
import ar.edu.itba.paw.webapp.forms.UserCreateForm;
import ar.edu.itba.paw.webapp.forms.UserLoginForm;

import java.util.List;

@Path("users")
@Component
public class UserController extends AuthController {

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
	public Response createUser(final UserCreateForm form) {
		// Check if the user is valid
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
		// TODO: Uncomment this line
//		es.sendRegistrationEmail(user);
		
		final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getId())).build();

		return Response.created(uri).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTrip(final TripCreateForm form) {
		// Create trip with logged user as a driver
		User loggedUser = user();
		Trip trip = ts.register(form.getTrip(), loggedUser);
		
		final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(trip.getId())).build();
		return Response.created(uri).build();
	}
	
	@GET
	@Path("/{id}/trips")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOwnTrips(@PathParam("id") final int id) {
		// TODO: Check permissions
		final User user = us.getById(id);
		if (user != null) Response.status(Status.NOT_FOUND).build();
		
		List<Trip> trips = ts.getUserTrips(user);
		
		// TODO: Return list of trips
		return Response.noContent().build();
	}
	
	@GET
	@Path("/{id}/reviews")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReviews(@PathParam("id") final int id) {
		final User user = us.getById(id);
		if (user != null) Response.status(Status.NOT_FOUND).build();
		
		List<Review> reviews = rs.getReviews(user);
		
		// TODO: Return list of reviews
		return Response.noContent().build();
	}
	
	@GET
	@Path("/{id}/history")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHistory(@PathParam("id") final int id) {
		final User user = us.getById(id);
		if (user != null) Response.status(Status.NOT_FOUND).build();
		
		List<History> histories = hs.getHistories(user);
		
		// TODO: Return list of histories
		return Response.noContent().build();
	}
	
	// TODO: Check if there's a more RESTful way to do this
	// Reservations should be linked to trips. Does this make sense?
	@GET
	@Path("/{id}/reservations")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReservations(@PathParam("id") final int id) {
		// TODO: Check permissions
		final User user = us.getById(id);
		if (user != null) Response.status(Status.NOT_FOUND).build();
		
		List<Trip> trips = ts.getReservedTrips(user);
		
		// TODO: Return list of trips
		return Response.noContent().build();
	}
}
