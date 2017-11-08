package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EventService;
import ar.edu.itba.paw.interfaces.HistoryService;
import ar.edu.itba.paw.interfaces.TripService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.models.User;

@Service
public class EventServiceImpl implements  EventService {
	
	@Autowired
	HistoryService hs;
	
	@Autowired
	TripService ts;
	
	public void registerReserve(User user, Integer tripId) {
		hs.addHistory(user, tripId, "RESERVE");
		// TODO: Change DB. Users usernames should be valid emails.
		EmailService.INSTANCE.sendReservationEmail(user, ts.findById(tripId));
		
	}
	
	public void registerUnreserve(User user, Integer tripId) {
		hs.addHistory(user, tripId, "UNRESERVE");
		// TODO: Change DB. Users usernames should be valid emails.
		EmailService.INSTANCE.sendUnreservationEmail(user, ts.findById(tripId));
	}
}
