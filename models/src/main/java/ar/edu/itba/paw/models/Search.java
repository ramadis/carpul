package ar.edu.itba.paw.models;

import java.sql.Timestamp;

public class Search {
	private String from;
	private String to;
	private Timestamp when;
	
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
	public Timestamp getWhen() {
		return when;
	}
	
	public void setWhen(Long when) {
		this.when = new Timestamp(when);
	}
	
	public void setWhen(Timestamp when) {
		this.when = when;
	}
	
	public Search() {}
	
	public Search(String from, String to, Timestamp when) {
		this.from = from;
		this.to = to;
		this.when = when;
	}
}
