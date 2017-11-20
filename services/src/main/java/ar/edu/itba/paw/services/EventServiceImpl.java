package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.EventService;
import ar.edu.itba.paw.interfaces.HistoryService;
import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.interfaces.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

@Service
public class EventServiceImpl implements  EventService {
	
	@Autowired
	private HistoryService hs;
	
	@Autowired
	private TripService ts;
	
	@Autowired
	private UserService us;
	
	@Autowired
	private EmailService es;
	
	public void registerReserve(User user, Integer tripId) {
		hs.addHistory(user, tripId, "RESERVE", false);
		// TODO: Change DB. Users usernames should be valid emails.
		es.sendReservationEmail(user, ts.findById(tripId));
		
	}
	
	public void registerUnreserve(User user, Integer tripId) {
		hs.addHistory(user, tripId, "UNRESERVE", false);
		// TODO: Change DB. Users usernames should be valid emails.
		es.sendUnreservationEmail(user, ts.findById(tripId));
	}
	
	// TODO: Run migration ALTER TABLE histories ADD own boolean
	// UPDATE histories SET own = false;
	public void registerKicked(User user, Integer tripId) {
		hs.addHistory(user, tripId, "KICKED", true);
		// TODO: Change DB. Users usernames should be valid emails.
		es.sendUnreservationEmail(user, ts.findById(tripId));
	}
	
	public void registerDelete(User user, Integer tripId) {
		//TODO: Check if it works
		// Create trip wrapper
		Trip trip = new Trip();
		trip.setId(tripId);
		
		List<User> passengers = us.getPassengers(trip);
		
		// Add an event for every passenger and alert them via email.
		passengers.forEach((passenger) -> {
			hs.addHistory(passenger, tripId, "DELETE", true);
			es.sendDeletionEmail(passenger, ts.findById(tripId));
		});
	}
}
