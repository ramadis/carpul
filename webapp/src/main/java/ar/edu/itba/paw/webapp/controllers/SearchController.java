package ar.edu.itba.paw.webapp.controllers;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.webapp.DTO.ReservationDTO;
import ar.edu.itba.paw.webapp.DTO.TripDTO;
import ar.edu.itba.paw.models.User;

@Path("search")
@Component
public class SearchController extends AuthController {

	@Autowired
	private TripService ts;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@DefaultValue("") @QueryParam("from") String from,
						   @DefaultValue("") @QueryParam("to") String to,
						   @DefaultValue("") @QueryParam("when") Long when,
						   @DefaultValue("true") @QueryParam("exclude_driver") Boolean excludeDriver,
						   @DefaultValue("0") @QueryParam("page") int page,
						   @DefaultValue("5") @QueryParam("per_page") int perPage) {
		
		// Create a valid search model.
		Search search = new Search();
		search.setFrom(from);
		search.setTo(to);
		search.setWhen(when == null ? System.currentTimeMillis() : when);
		
		// Get logged user
		User user = user();
		
		// Get trips to this search
		List<ReservationDTO> tripDTOs = new ArrayList<>();
		List<Trip> trips = ts.findByRoute(search, new Pagination(page, perPage), excludeDriver ? user : null);
		
		// If no trips at all. It's empty.
		if (trips.isEmpty()) return Response.ok(Collections.EMPTY_LIST).build();

		// Generate DTOs
		for(Trip t: trips) tripDTOs.add(new ReservationDTO(t, user));
		return Response.ok(tripDTOs).build();
	}
	
	@GET
	@Path("/suggestions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response suggest(@DefaultValue("'") @QueryParam("origin") String origin,
							@DefaultValue("false") @QueryParam("exclude_driver") Boolean excludeDriver,
						 	@DefaultValue("0") @QueryParam("page") int page,
						 	@DefaultValue("5") @QueryParam("per_page") int perPage) {
		// Get logged user
		User user = user();

		// Get trips to this search
		List<ReservationDTO> tripDTOs = new ArrayList<>();
		List<Trip> trips = ts.getSuggestions(origin, new Pagination(page, perPage), excludeDriver ? null : user);
		
		// If no trips at all. It's empty.
		if (trips.isEmpty()) return Response.ok(Collections.EMPTY_LIST).build();
		
		// Generate DTOs
		for(Trip t: trips) tripDTOs.add(new ReservationDTO(t, user));
		return Response.ok(tripDTOs).build();
	}
}
