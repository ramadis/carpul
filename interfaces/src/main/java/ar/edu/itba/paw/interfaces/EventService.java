package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

public interface EventService {
	void registerReserve(User user, Integer tripId);
	void registerUnreserve(User user, Integer tripId);
	void registerDelete(User user, Integer tripId);
}

