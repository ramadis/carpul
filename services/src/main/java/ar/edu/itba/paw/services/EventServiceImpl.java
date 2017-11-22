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
		es.sendReservationEmail(user, ts.findById(tripId));
	}

	public void registerUnreserve(User user, Integer tripId) {
		hs.addHistory(user, tripId, "UNRESERVE", false);
		es.sendUnreservationEmail(user, ts.findById(tripId));
	}

	public void registerKicked(User user, Integer tripId) {
		hs.addHistory(user, tripId, "KICKED", true);
		es.sendDeletionEmail(user, ts.findById(tripId));
	}

	public void registerDelete(User user, Integer tripId) {
		Trip trip = ts.findById(tripId);

		List<User> passengers = trip.getPassengers();

		// Add an event for every passenger and alert them via email.
		passengers.forEach((passenger) -> {
			hs.addHistory(passenger, tripId, "DELETE", true);
			es.sendDeletionEmail(passenger, trip);
		});
	}
}
