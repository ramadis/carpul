package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import ar.edu.itba.paw.models.Search;

public class SearchForm {
	@NotBlank
	@Pattern(regexp = "[a-zA-Z ]+")
	private String from;
	
	@NotBlank
	@Pattern(regexp = "[a-zA-Z ]+")
	private String to;
	
	@NotNull
	private Long when;
	
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

	public Long getWhen() {
		return when;
	}

	public void setWhen(Long when) {
		this.when = when;
	}

	public Search getSearch() {
		Search search = new Search();
		search.setFrom(from);
		search.setTo(to);
		search.setWhen(when);
		return search;
	}
}
