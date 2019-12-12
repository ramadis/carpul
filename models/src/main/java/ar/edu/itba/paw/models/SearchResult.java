package ar.edu.itba.paw.models;

import java.util.List;

public class SearchResult {
	private List<Trip> results;
	private Long count;
	
	public SearchResult() {}
	
	public SearchResult (List<Trip> results, Long count) {
		this.results = results;
		this.count = count;
	}
	
	public List<Trip> getResults() {
		return results;
	}
	public void setResults(List<Trip> results) {
		this.results = results;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
	public Boolean hasTrips() {
		return results != null && !results.isEmpty();
	}
	
	
}
