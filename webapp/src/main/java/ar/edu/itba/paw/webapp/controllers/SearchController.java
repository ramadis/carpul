package ar.edu.itba.paw.webapp.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
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
import ar.edu.itba.paw.models.User;

@Path("search")
@Consumes({MediaType.APPLICATION_JSON, "application/vnd.carpul.v1+json"})
@Produces({MediaType.APPLICATION_JSON, "application/vnd.carpul.v1+json"})
@Component
public class SearchController extends AuthController {

	@Autowired
	private TripService ts;
	
	@Context
	private UriInfo uriInfo;
	
	@GET
	@Path("/")
	public Response searchClosest(@DefaultValue("") @QueryParam("from") String from,
						   @DefaultValue("") @QueryParam("to") String to,
						   @QueryParam("arrLat") Double arrLat,
						   @QueryParam("arrLon") Double arrLon,
						   @QueryParam("depLat") Double depLat,
						   @QueryParam("depLon") Double depLon,
						   @DefaultValue("") @QueryParam("when") Long when,
						   @DefaultValue("true") @QueryParam("exclude_driver") Boolean excludeDriver,
						   @DefaultValue("0") @QueryParam("page") int page,
						   @DefaultValue("5") @QueryParam("per_page") int perPage) {
		
		// Create a valid search model.
		Search search = new Search();
		search.setFrom(from);
		search.setTo(to);
		search.setWhen(when == null ? System.currentTimeMillis() : Math.max(System.currentTimeMillis(), when));
		search.setArrival(new Position(arrLat, arrLon));
		search.setDeparture(new Position(depLat, depLon));
		
		// Get logged user
		User user = user();
		
		// Get trips to this search
		List<UnauthTripDTO> tripDTOs = new ArrayList<>();
		List<Trip> trips = ts.searchTrips(search, new Pagination(page, perPage), excludeDriver ? user : null);
		
		// If no trips at all. It's empty.
		if (trips.isEmpty()) return Response.ok(Collections.EMPTY_LIST).build();

		// Generate DTOs
		final URI userUri = uriInfo.getBaseUriBuilder().path("/users/").build();
		for(Trip t: trips) tripDTOs.add(new UnauthTripDTO(t, user, userUri));
		return Response.ok(tripDTOs).build();
	}
}
