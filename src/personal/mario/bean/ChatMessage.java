package personal.mario.bean;

public class ChatMessage {
	private String date;
	private String username;
	private String message;
	
	public ChatMessage() {}
	
	public ChatMessage(String date, String username, String message) {
		this.date = date;
		this.username = username;
		this.message = message;
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
