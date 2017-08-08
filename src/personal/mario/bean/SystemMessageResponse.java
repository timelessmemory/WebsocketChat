package personal.mario.bean;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SystemMessageResponse {
	private String type;
	private String username;
	private String content;
	private ConcurrentLinkedQueue<String> members;
	
	public SystemMessageResponse() {}
	
	public SystemMessageResponse(String type, String username, String content, ConcurrentLinkedQueue<String> members) {
		this.type = type;
		this.username = username;
		this.content = content;
		this.members = members;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public ConcurrentLinkedQueue<String> getMembers() {
		return members;
	}

	public void setMembers(ConcurrentLinkedQueue<String> members) {
		this.members = members;
	}
}
