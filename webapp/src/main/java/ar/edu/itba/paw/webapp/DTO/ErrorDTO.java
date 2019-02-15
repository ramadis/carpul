package ar.edu.itba.paw.webapp.DTO;

public class ErrorDTO {
	private int code;
	private String title;
	private String message;
	
	public ErrorDTO(int code, String title, String message) {
		this.code = code;
		this.title = title;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
