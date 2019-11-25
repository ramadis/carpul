package ar.edu.itba.paw.webapp.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.models.Review;

@Path("reviews")
@Component
public class ReviewController extends AuthController {

	@Autowired
	private ReviewService rs;

	@GET
	@Path("/:id")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") final int id) {
		
		Review review = rs.getReviewById(id);
		if (review == null) return Response.status(Status.NOT_FOUND).build();

		// TODO: Return review
		return Response.ok(review).build();
	}
}
