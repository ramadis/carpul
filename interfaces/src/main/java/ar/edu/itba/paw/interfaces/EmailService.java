package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface EmailService {
	void sendRegistrationEmail(User user);
	void sendReservationEmail(User user, Trip trip);
	void sendUnreservationEmail(User user, Trip trip);
	void sendDeletionEmail(User user, Trip trip);
}

