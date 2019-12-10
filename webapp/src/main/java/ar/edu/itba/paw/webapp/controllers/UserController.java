package ar.edu.itba.paw.webapp.controllers;

import javax.validation.Validator;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

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
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.webapp.DTO.ReviewDTO;
import ar.edu.itba.paw.webapp.DTO.ErrorDTO;
import ar.edu.itba.paw.webapp.DTO.HistoryDTO;
import ar.edu.itba.paw.webapp.DTO.ReservationDTO;
import ar.edu.itba.paw.webapp.DTO.TripDTO;
import ar.edu.itba.paw.webapp.DTO.UserDTO;
import ar.edu.itba.paw.webapp.forms.ImageForm;
import ar.edu.itba.paw.webapp.forms.UserCreateForm;
import ar.edu.itba.paw.webapp.forms.UserUpdateForm;
import ar.edu.itba.paw.models.User;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
	public Response createUser(final UserCreateForm form) {
		// Check if the user form is valid
		if (form == null) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorDTO(Status.BAD_REQUEST.getStatusCode(), "form", "form is null")).build();
		}
		
		if (!validator.validate(form).isEmpty()) {
			List<ErrorDTO> errors = validator.validate(form).stream().map(validation -> new ErrorDTO(Status.BAD_REQUEST.getStatusCode(), validation.getPropertyPath() + "", validation.getMessage())).collect(Collectors.toList());
			return Response.status(Status.BAD_REQUEST).entity(errors).build();
		}

		console.info("Controller: Start creating user {}", form.getUsername());
		
		// Get user from form
		User user = form.getUser();
		
		// Check if user exists
		if (us.exists(user)) {
			return Response.status(Status.CONFLICT).entity(new ErrorDTO(Status.CONFLICT.getStatusCode(), "username", "user already exists")).build();
		}

		// Register new user
		user = us.register(user);

		// Send welcome email to user
		es.sendRegistrationEmail(user);
		
		final URI uri = uriInfo.getBaseUriBuilder().path("/users/{id}").build(user.getId());
		return Response.created(uri).entity(new UserDTO(user, uri)).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	// TODO: This endpoint is working
	public Response getById(@PathParam("id") final int id) {
		console.info("Controller: Getting user with id: {}", id);
		final User user = us.getById(id);
		
		if (user != null) {
			return Response.ok(new UserDTO(user, uriInfo.getBaseUriBuilder().path("/users/{id}").build(user.getId()))).build();
		}
		
		return Response.status(Status.NOT_FOUND).entity(new ErrorDTO(Status.NOT_FOUND.getStatusCode(), "id", "User not found")).build();
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentUser() {
		final User user = user();
		return Response.ok(new UserDTO(user, uriInfo.getBaseUriBuilder().path("/users/{id}").build(user.getId()))).build();
	}
	
	@GET
	@Path("/{id}/trips")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTrips(@PathParam("id") final int id,
								@DefaultValue("0") @QueryParam("page") int page,
								@DefaultValue("3") @QueryParam("per_page") int perPage) {
		console.info("Controller: Getting trips for user with id: {}", id);
		
		final User user = us.getById(id);
		final User loggedUser = user();
		if (user == null) return Response.status(Status.NOT_FOUND).entity(new ErrorDTO(Status.NOT_FOUND.getStatusCode(), "id", "the user does not exist")).build();
		
		// Search trips belonging to a given user
		List<Trip> trips = ts.getUserTrips(user, new Pagination(page, perPage));
		if (trips == null || trips.isEmpty()) return Response.ok(Collections.EMPTY_LIST).build();
	
		// Return trip DTOs
		if (loggedUser != null && loggedUser.getId().equals(id)) {			
			List<TripDTO> tripDTOs = trips.stream().map(trip -> new TripDTO(trip)).collect(Collectors.toList());
			return Response.ok(tripDTOs).build();
		} else {
			List<ReservationDTO> tripDTOs = trips.stream().map(trip -> new ReservationDTO(trip, loggedUser)).collect(Collectors.toList());
			return Response.ok(tripDTOs).build();
		}
	}
	
	@GET
	@Path("/{id}/reviews")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReviews(@PathParam("id") final int id,
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("per_page") int perPage) {
		console.info("Controller: Getting reviews for user with id: {}", id);
		
		final User user = us.getById(id);
		if (user == null) return Response.status(Status.NOT_FOUND).entity(new ErrorDTO(Status.NOT_FOUND.getStatusCode(), "id", "the user does not exist")).build();
		
		// Transform reviews to DTOs
		List<ReviewDTO> reviewDTOs = new ArrayList<>();
		List<Review> reviews = rs.getReviews(user, new Pagination(page, perPage));
		if (reviews == null || reviews.isEmpty()) return Response.ok(Collections.EMPTY_LIST).build();
		
		// Return reviews for a given user id
		for (Review r: reviews) reviewDTOs.add(new ReviewDTO(r, uriInfo.getAbsolutePathBuilder().path(String.valueOf(r.getId())).build().toString()));
		return Response.ok(reviewDTOs).build();
	}
	
	@GET
	@Path("/{id}/history")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHistory(@PathParam("id") final int id,
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("per_page") int perPage) {
		console.info("Controller: Getting history for user with id: {}", id);
		
		final User user = us.getById(id);
		if (user == null) return Response.status(Status.NOT_FOUND).entity(new ErrorDTO(Status.NOT_FOUND.getStatusCode(), "id", "the user does not exist")).build();
		
		List<History> histories = hs.getHistories(user, new Pagination(page, perPage));
		if (histories == null || histories.isEmpty()) return Response.ok(Collections.EMPTY_LIST).build();

		
		List<HistoryDTO> historyDTOs = histories.stream().map(history -> new HistoryDTO(history)).collect(Collectors.toList());
		return Response.ok(historyDTOs).build();
	}
	
	@GET
	@Path("/{id}/reservations")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReservations(@PathParam("id") final int id,
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("3") @QueryParam("per_page") int perPage) {
		console.info("Controller: Getting reservations for user with id: {}", id);
		
		final User user = us.getById(id);
		if (user == null) return Response.status(Status.NOT_FOUND).entity(new ErrorDTO(Status.NOT_FOUND.getStatusCode(), "id", "the user does not exist")).build();
		
		// Search trips belonging to a given user
		List<Trip> trips = ts.getReservedTrips(user, new Pagination(page, perPage));

		if (trips == null || trips.isEmpty()) return Response.ok(Collections.EMPTY_LIST).build();
		
		// Return trips owned by the user with the param id
		List<ReservationDTO> tripDTOs = trips.stream().map(trip -> new ReservationDTO(trip, user)).collect(Collectors.toList());
		return Response.ok(tripDTOs).build();
	}
	
	@PUT
    @Path("/{id}/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadProfileImage(@PathParam("id") Integer id, @BeanParam final ImageForm form){
		console.info("Controller: Updating profile image for user with id: {}", id);
		
		if (form == null) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorDTO(Status.BAD_REQUEST.getStatusCode(), "form", "form is null")).build();
		}
		
		if (!validator.validate(form).isEmpty()) {
			List<ErrorDTO> errors = validator.validate(form).stream().map(validation -> new ErrorDTO(Status.BAD_REQUEST.getStatusCode(), validation.getPropertyPath() + "", validation.getMessage())).collect(Collectors.toList());
			return Response.status(Status.BAD_REQUEST).entity(errors).build();
		}

		User user = us.getById(id);
		User loggedUser = user(); 
		
		if (user == null) return Response.status(Status.NOT_FOUND).entity(new ErrorDTO(Status.NOT_FOUND.getStatusCode(), "id", "the user does not exist")).build();
		if (!user.getId().equals(loggedUser.getId())) return Response.status(Status.FORBIDDEN).entity(new ErrorDTO(Status.FORBIDDEN.getStatusCode(), "id", "logged with wrong user")).build();
		byte[] prevImage = user.getProfileImage();
		
		us.uploadProfileImage(user, form.getContent());
		
		final URI uri = uriInfo.getBaseUriBuilder().path("/users/{id}/image").build(user.getId());
		if ( prevImage == null) return Response.created(uri).build();
		return Response.status(Status.NO_CONTENT).build();

    }
	
	@GET
	@Path("/{id}/image")
	@Produces({"image/jpg", "image/png", "image/gif", "image/jpeg"})
	public Response getProfileImage(@PathParam("id") final int id) {
		User user = us.getById(id);
		console.info("Getting profile image for id {}", id);
		if (user == null || user.getProfileImage() == null) return Response.status(Status.NOT_FOUND).build();
		
		return Response.ok(user.getProfileImage()).build();
	}
	
	@PUT
    @Path("/{id}/cover")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadCoverImage(@PathParam("id") Integer id, @BeanParam final ImageForm form){
		console.info("Controller: Updating cover image to user {}", id);
		if (form == null) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorDTO(Status.BAD_REQUEST.getStatusCode(), "form", "form is null")).build();
		}
		
		if (!validator.validate(form).isEmpty()) {
			List<ErrorDTO> errors = validator.validate(form).stream().map(validation -> new ErrorDTO(Status.BAD_REQUEST.getStatusCode(), validation.getPropertyPath() + "", validation.getMessage())).collect(Collectors.toList());
			return Response.status(Status.BAD_REQUEST).entity(errors).build();
		}
		
		User user = us.getById(id);
		User loggedUser = user(); 
		
		if (user == null) return Response.status(Status.NOT_FOUND).entity(new ErrorDTO(Status.NOT_FOUND.getStatusCode(), "id", "the user does not exist")).build();
		if (!user.getId().equals(loggedUser.getId())) return Response.status(Status.FORBIDDEN).entity(new ErrorDTO(Status.FORBIDDEN.getStatusCode(), "id", "logged with wrong user")).build();
		byte[] prevImage = user.getCoverImage();
		
		us.uploadCoverImage(user, form.getContent());
		
		final URI uri = uriInfo.getBaseUriBuilder().path("/users/{id}/cover").build(user.getId());
		if ( prevImage == null) return Response.created(uri).build();
		return Response.status(Status.NO_CONTENT).build();
    }
	
	@PUT
	@Path("/{id}/profile")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("id") Integer id, final UserUpdateForm form) {
		console.info("Controller: Start updating user {}", id);
		
		if (form == null) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorDTO(Status.BAD_REQUEST.getStatusCode(), "form", "form is null")).build();
		}
		
		if (!validator.validate(form).isEmpty()) {
			List<ErrorDTO> errors = validator.validate(form).stream().map(validation -> new ErrorDTO(Status.BAD_REQUEST.getStatusCode(), validation.getPropertyPath() + "", validation.getMessage())).collect(Collectors.toList());
			return Response.status(Status.BAD_REQUEST).entity(errors).build();
		}
		
		User toUpdate = us.getById(id);
		User loggedUser = user();
		
		// Check if profile can be updated
		if (toUpdate == null) return Response.status(Status.NOT_FOUND).entity(new ErrorDTO(Status.NOT_FOUND.getStatusCode(), "id", "the user does not exist")).build();
		if (!toUpdate.getId().equals(loggedUser.getId())) return Response.status(Status.FORBIDDEN).entity(new ErrorDTO(Status.FORBIDDEN.getStatusCode(), "id", "logged with wrong user")).build();
		
		// Get user from form
		User user = form.getUser();
		
		// Update user fields
		user = us.update(toUpdate, user);

		return Response.ok().entity(new UserDTO(user, uriInfo.getBaseUriBuilder().path("/users/{id}").build(user.getId()))).build();
	}
	
	@GET
	@Path("/{id}/cover")
	@Produces({"images/jpg", "images/png", "images/gif"})
	public Response getCoverImage(@PathParam("id") final int id) {
		User user = us.getById(id);
		console.info("Getting cover image for id {}", id);
		if (user == null || user.getCoverImage() == null) return Response.status(Status.NOT_FOUND).build();
		
		return Response.ok(user.getCoverImage()).build();
	}
}
