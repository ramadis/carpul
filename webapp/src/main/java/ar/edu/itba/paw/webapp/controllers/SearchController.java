package ar.edu.itba.paw.webapp.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.webapp.DTO.ResultDTO;
import ar.edu.itba.paw.webapp.DTO.TripDTO;
import ar.edu.itba.paw.webapp.config.WebAuth;
import ar.edu.itba.paw.webapp.forms.SearchForm;
import ar.edu.itba.paw.models.User;

@Path("search")
@Component
public class SearchController extends AuthController {
    private final static Logger console = LoggerFactory.getLogger(WebAuth.class);

	@Autowired
	private TripService ts;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchAllView(@QueryParam("from") String from,
								  @QueryParam("to") String to,
								  @QueryParam("when") Long when) {
		// Create a valid search model.
		Search search = new Search();
		search.setFrom(from.split(",")[0]);
		search.setTo(to.split(",")[0]);
		search.setWhen(when);
		
		// Get logged user
		User user = user();

		// Get trips to this search
		List<TripDTO> tripDTOs = new ArrayList<>();
		List<Trip> trips = user == null ? ts.findByRoute(search) : ts.findByRoute(user, search);
		
		// If no trips at all. It's empty.
		if (trips.isEmpty()) return Response.noContent().build();

		// Generate DTOs
		for(Trip t: trips) tripDTOs.add(new TripDTO(t));
		return Response.ok(tripDTOs).build();
	}
	
	@GET
	@Path("/future")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchAllViewFuture(@QueryParam("from") String from,
								  @QueryParam("to") String to,
								  @QueryParam("when") Long when) {
		// Create a valid search model.
		Search search = new Search();
		search.setFrom(from.split(",")[0]);
		search.setTo(to.split(",")[0]);
		search.setWhen(when);

		// Get logged user
		User user = user();

		// Get trips to this search
		List<TripDTO> laterTripDTOs = new ArrayList<>();
		List<Trip> laterTrips = user == null ? ts.findAfterDateByRoute(search) : ts.findAfterDateByRoute(user, search);
		
		// If no trips at all. It's empty.
		if (laterTrips.isEmpty()) return Response.noContent().build();

		// Generate DTOs
		for(Trip t: laterTrips) laterTripDTOs.add(new TripDTO(t));
		return Response.ok(laterTripDTOs).build();
	}

	// TODO: Check wtf is this view.
//	@RequestMapping(value = "/search/{tripId}", method = RequestMethod.GET)
//	public ModelAndView searchAllView(@PathVariable("tripId") final Integer tripId,
//									 @RequestParam("from") String from,
//									 @RequestParam("to") String to,
//									 @RequestParam("when") Timestamp when) {
//		// Get trip by id
//		List<Trip> trips = new ArrayList<>();
//		trips.add(ts.findById(tripId));
//
//		// Expose view
//		final ModelAndView mav = new ModelAndView("trips/individual");
//		mav.addObject("trips", trips);
//		mav.addObject("is_searching", true);
//		return mav;
//	}
}
