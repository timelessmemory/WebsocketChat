package personal.mario.bean;

public class CommonMessageResponse {

	private String type;
	private String date;
	private String username;
	private String message;
	
	public CommonMessageResponse() {
	}
	
	public CommonMessageResponse(String type, String date, String username, String message) {
		this.type = type;
		this.date = date;
		this.username = username;
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
