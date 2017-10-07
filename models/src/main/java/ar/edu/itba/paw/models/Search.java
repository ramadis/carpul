package ar.edu.itba.paw.models;

public class Search {
	private String from;
	private String to;
	private String when;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getWhen() {
		return when;
	}
	public void setWhen(String when) {
		this.when = when;
	}
	
	public Search() {
		this.from = "";
		this.to = "";
		this.when = "";
	}
	
	public Search(String from, String to, String when) {
		this.from = from;
		this.to = to;
		this.when = when;
	}
}
