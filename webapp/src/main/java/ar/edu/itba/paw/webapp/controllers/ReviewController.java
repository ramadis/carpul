package ar.edu.itba.paw.webapp.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Validator;
import javax.ws.rs.BeanParam;
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

import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.DTO.ErrorDTO;
import ar.edu.itba.paw.webapp.DTO.ReviewDTO;
import ar.edu.itba.paw.webapp.forms.ImageForm;

@Path("reviews")
@Component
public class ReviewController extends AuthController {
	private final static Logger console = LoggerFactory.getLogger(TripController.class);
	
	@Autowired
	private ReviewService rs;
	
	@Autowired
    private Validator validator;
	
	@Context
	private UriInfo uriInfo;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") final int id) {
		console.info("Controller: Getting review with id {}", id);
		Review review = rs.getReviewById(id);
		if (review == null) return Response.status(Status.NOT_FOUND).entity(new ErrorDTO(Status.NOT_FOUND.getStatusCode(), "id", "review not found")).build();

		URI uri = uriInfo.getBaseUriBuilder().path("/users/").build();
		return Response.ok(new ReviewDTO(review, uri)).build();
	}
	
	@PUT
    @Path("/{id}/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImage(@PathParam("id") Integer id, @BeanParam final ImageForm form){
		if (form == null) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorDTO(Status.BAD_REQUEST.getStatusCode(), "form", "form is null")).build();
		}
		
		if (!validator.validate(form).isEmpty()) {
			List<ErrorDTO> errors = validator.validate(form).stream().map(validation -> new ErrorDTO(Status.BAD_REQUEST.getStatusCode(), validation.getPropertyPath() + "", validation.getMessage())).collect(Collectors.toList());
			return Response.status(Status.BAD_REQUEST).entity(errors).build();
		}
		
		Review review = rs.getReviewById(id);
		User loggedUser = user(); 
		
		console.info("Controller: Uploading image to review {}", id);
		if (review == null) return Response.status(Status.NOT_FOUND).entity(new ErrorDTO(Status.NOT_FOUND.getStatusCode(), "id", "review not found")).build();
		if (!review.getOwner().getId().equals(loggedUser.getId())) return Response.status(Status.FORBIDDEN).entity(new ErrorDTO(Status.FORBIDDEN.getStatusCode(), "id", "logged with wrong user")).build();
		if (review.getImage() != null) return Response.status(Status.CONFLICT).entity(new ErrorDTO(Status.CONFLICT.getStatusCode(), "uploaded", "the image was already uploaded")).build();
		
		rs.uploadImage(review, form.getContent());

		final URI uri = uriInfo.getBaseUriBuilder().path("/reviews/{id}/image").build(id);
		return Response.created(uri).build();

    }
	
	@GET
	@Path("/{id}/image")
	@Produces({"image/jpg", "image/png", "image/gif", "image/jpeg"})
	public Response getImageById(@PathParam("id") final int id) {
		Review review = rs.getReviewById(id);
		console.info("Controller: Getting image for id {}", id);
		if (review == null || review.getImage() == null) return Response.status(Status.NOT_FOUND).build();
		
		return Response.ok(review.getImage()).build();
	}
}
