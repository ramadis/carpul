package ar.edu.itba.paw.webapp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.DTO.ErrorDTO;
import ar.edu.itba.paw.webapp.DTO.UserDTO;
import ar.edu.itba.paw.webapp.forms.UserUpdateForm;

@Path("user")
@Component
public class AuthUserController extends AuthController {
	private final static Logger console = LoggerFactory.getLogger(TripController.class);
	
	@Context
	private UriInfo uriInfo;
	
	@Autowired
    private Validator validator;
	
	@Autowired
	private UserService us;
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentUser() {
		final User user = user();
		return Response.ok(new UserDTO(user, uriInfo.getBaseUriBuilder().path("/users/{id}").build(user.getId()))).build();
	}
	
	@PUT
	@Path("/")
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
		
		User toUpdate = user();
		
		// Get user from form
		User user = form.getUser();
		
		// Update user fields
		user = us.update(toUpdate, user);

		return Response.ok().entity(new UserDTO(user, uriInfo.getBaseUriBuilder().path("/users/{id}").build(user.getId()))).build();
	}
}