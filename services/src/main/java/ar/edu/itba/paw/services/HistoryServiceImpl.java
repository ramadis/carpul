package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.HistoryDao;
import ar.edu.itba.paw.interfaces.HistoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.models.History;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.User;

@Service
public class HistoryServiceImpl implements  HistoryService {

	@Autowired
	private HistoryDao historyDao;
	
	public List<History> getHistories(User user, Pagination pagination) {
		return historyDao.getHistories(user, pagination);
	}
	
	@Transactional
	public History addHistory(User user, Integer tripId, String type, Boolean own) {
		return historyDao.addHistory(user, tripId, type, own);
	}
}
