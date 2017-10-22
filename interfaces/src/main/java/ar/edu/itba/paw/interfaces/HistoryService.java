package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.models.History;
import ar.edu.itba.paw.models.User;

public interface HistoryService {
	List<History> getHistories(User user);
	List<History> getHistoriesByPage(User user, Integer page);
	Boolean addHistory(User user, Integer tripId, String type);
}

