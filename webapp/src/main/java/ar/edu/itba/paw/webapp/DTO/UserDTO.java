package ar.edu.itba.paw.webapp.DTO;
import ar.edu.itba.paw.models.User;
import java.sql.Timestamp;

public class UserDTO {
    private String username;
    private String first_name;
    private String last_name;
    private Timestamp created;
    private Integer rating;
    private String image;
    private String cover;
    private long id;

    public UserDTO() {}

    public UserDTO(User user) {
        this.setUsername(user.getUsername());
        this.setId(user.getId());
        this.setFirst_name(user.getFirst_name());
        this.setCreated(user.getCreated());
        this.setLast_name(user.getLast_name());
        this.rating = user.getRating();
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
