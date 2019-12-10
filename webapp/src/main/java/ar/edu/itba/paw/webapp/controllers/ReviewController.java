package ar.edu.itba.paw.webapp.controllers;

import javax.validation.Validator;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
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

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") final int id) {
		console.info("Getting review {}", id);
		Review review = rs.getReviewById(id);
		if (review == null) return Response.status(Status.NOT_FOUND).build();

		// TODO: Return review
		return Response.ok(new ReviewDTO(review)).build();
	}
	
	@PUT
    @Path("/{id}/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImage(@PathParam("id") Integer id, @BeanParam final ImageForm form){
		if (form == null || !validator.validate(form).isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		Review review = rs.getReviewById(id);
		User loggedUser = user(); 
		
		console.info("Uploading image to review {}", id);
		if (review == null) return Response.status(Status.NOT_FOUND).build();
		if (!review.getOwner().getId().equals(loggedUser.getId())) return Response.status(Status.FORBIDDEN).build();
		if (review.getImage() != null) return Response.status(Status.CONFLICT).build();
		
		rs.uploadImage(review, form.getContent());
		return Response.status(Status.CREATED).build();

    }
	
	@GET
	@Path("/{id}/image")
	@Produces({"image/jpg", "image/png", "image/gif", "image/jpeg"})
	public Response getImageById(@PathParam("id") final int id) {
		Review review = rs.getReviewById(id);
		console.info("Getting image for id {}", id);
		if (review == null || review.getImage() == null) return Response.status(Status.NOT_FOUND).build();
		
		// TODO: Return review
		return Response.ok(review.getImage()).build();
	}
}
