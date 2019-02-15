package ar.edu.itba.paw.webapp.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.webapp.forms.SearchForm;
import ar.edu.itba.paw.models.User;

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

		// TODO: Return List of trips
		return Response.ok(review).build();
	}
}
