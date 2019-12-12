package ar.edu.itba.paw.webapp.controllers;

import java.net.URI;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Position;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.webapp.DTO.UnauthTripDTO;
import ar.edu.itba.paw.webapp.DTO.TripDTO;
import ar.edu.itba.paw.models.User;

@Path("search")
@Component
public class SearchController extends AuthController {

	@Autowired
	private TripService ts;
	
	@Context
	private UriInfo uriInfo;
	
	@GET
	@Path("/closest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchClosest(@DefaultValue("") @QueryParam("from") String from,
						   @DefaultValue("") @QueryParam("to") String to,
						   @DefaultValue("") @QueryParam("when") Long when,
						   @DefaultValue("true") @QueryParam("exclude_driver") Boolean excludeDriver,
						   @DefaultValue("0") @QueryParam("page") int page,
						   @DefaultValue("5") @QueryParam("per_page") int perPage) {
		
		// Create a valid search model.
		Search search = new Search();
		search.setFrom(from);
		search.setTo(to);
		search.setWhen(when == null ? System.currentTimeMillis() : Math.max(System.currentTimeMillis(), when));
		search.setArrival(new Position());
		search.setDeparture(new Position());
		
		// Get logged user
		User user = user();
		
		// Get trips to this search
		List<UnauthTripDTO> tripDTOs = new ArrayList<>();
		List<Trip> trips = ts.searchByClosest(search, new Pagination(page, perPage), excludeDriver ? user : null);
		
		// If no trips at all. It's empty.
		if (trips.isEmpty()) return Response.ok(Collections.EMPTY_LIST).build();

		// Generate DTOs
		final URI userUri = uriInfo.getBaseUriBuilder().path("/users/").build();
		for(Trip t: trips) tripDTOs.add(new UnauthTripDTO(t, user, userUri));
		return Response.ok(tripDTOs).build();
	}
	
	@GET
	@Path("/origin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchOrigin(@DefaultValue("") @QueryParam("from") String from,
						   @DefaultValue("") @QueryParam("to") String to,
						   @DefaultValue("") @QueryParam("when") Long when,
						   @DefaultValue("true") @QueryParam("exclude_driver") Boolean excludeDriver,
						   @DefaultValue("0") @QueryParam("page") int page,
						   @DefaultValue("5") @QueryParam("per_page") int perPage) {
		
		// Create a valid search model.
		Search search = new Search();
		search.setFrom(from);
		search.setTo(to);
		search.setWhen(when == null ? System.currentTimeMillis() : Math.max(System.currentTimeMillis(), when));
		search.setArrival(new Position());
		search.setDeparture(new Position());
		
		// Get logged user
		User user = user();
		
		// Get trips to this search
		List<UnauthTripDTO> tripDTOs = new ArrayList<>();
		List<Trip> trips = ts.searchByOrigin(search, new Pagination(page, perPage), excludeDriver ? user : null);
		
		// If no trips at all. It's empty.
		if (trips.isEmpty()) return Response.ok(Collections.EMPTY_LIST).build();

		// Generate DTOs
		final URI userUri = uriInfo.getBaseUriBuilder().path("/users/").build();
		for(Trip t: trips) tripDTOs.add(new UnauthTripDTO(t, user, userUri));
		return Response.ok(tripDTOs).build();
	}
	
	@GET
	@Path("/rest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchRest(@DefaultValue("") @QueryParam("from") String from,
						   @DefaultValue("") @QueryParam("to") String to,
						   @DefaultValue("") @QueryParam("when") Long when,
						   @DefaultValue("true") @QueryParam("exclude_driver") Boolean excludeDriver,
						   @DefaultValue("0") @QueryParam("page") int page,
						   @DefaultValue("5") @QueryParam("per_page") int perPage) {
		
		// Create a valid search model.
		Search search = new Search();
		search.setFrom(from);
		search.setTo(to);
		search.setWhen(when == null ? System.currentTimeMillis() : Math.max(System.currentTimeMillis(), when));
		search.setArrival(new Position());
		search.setDeparture(new Position());
		
		// Get logged user
		User user = user();
		
		// Get trips to this search
		List<UnauthTripDTO> tripDTOs = new ArrayList<>();
		List<Trip> trips = ts.searchByRest(search, new Pagination(page, perPage), excludeDriver ? user : null);
		
		// If no trips at all. It's empty.
		if (trips.isEmpty()) return Response.ok(Collections.EMPTY_LIST).build();

		// Generate DTOs
		final URI userUri = uriInfo.getBaseUriBuilder().path("/users/").build();
		for(Trip t: trips) tripDTOs.add(new UnauthTripDTO(t, user, userUri));
		return Response.ok(tripDTOs).build();
	}

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
		search.setWhen(when == null ? System.currentTimeMillis() : Math.max(System.currentTimeMillis(), when));
		search.setArrival(new Position());
		search.setDeparture(new Position());
		
		// Get logged user
		User user = user();
		
		// Get trips to this search
		List<UnauthTripDTO> tripDTOs = new ArrayList<>();
		List<Trip> trips = ts.findByRoute(search, new Pagination(page, perPage), excludeDriver ? user : null);
		
		// If no trips at all. It's empty.
		if (trips.isEmpty()) return Response.ok(Collections.EMPTY_LIST).build();

		// Generate DTOs
		final URI userUri = uriInfo.getBaseUriBuilder().path("/users/").build();
		for(Trip t: trips) tripDTOs.add(new UnauthTripDTO(t, user, userUri));
		return Response.ok(tripDTOs).build();
	}
	
	@GET
	@Path("/suggestions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response suggest(@DefaultValue("'") @QueryParam("origin") String origin,
							@DefaultValue("true") @QueryParam("exclude_driver") Boolean excludeDriver,
						 	@DefaultValue("0") @QueryParam("page") int page,
						 	@DefaultValue("10") @QueryParam("per_page") int perPage) {
		// Get logged user
		User user = user();

		// Get trips to this search
		List<UnauthTripDTO> tripDTOs = new ArrayList<>();
		List<Trip> trips = ts.getSuggestions(origin, new Pagination(page, perPage), excludeDriver ? null : user);
		
		// If no trips at all. It's empty.
		if (trips.isEmpty()) return Response.ok(Collections.EMPTY_LIST).build();
		
		// Generate DTOs
		final URI userUri = uriInfo.getBaseUriBuilder().path("/users/").build();
		for(Trip t: trips) tripDTOs.add(new UnauthTripDTO(t, user, userUri));
		return Response.ok(tripDTOs).build();
	}
}
