package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import ar.edu.itba.paw.models.Review;

public class ReviewForm {
	@NotBlank
	@Pattern(regexp = "[a-zA-Z ]+")
	private String message;
	
	@NotNull
	@Min(0)
	@Max(5)
	private Integer stars;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}
	
	public Review getReview() {
		Review review = new Review();
		review.setStars(stars);
		review.setMessage(message);
		return review;
	}
}
