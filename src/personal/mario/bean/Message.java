package personal.mario.bean;

import java.io.Serializable;

public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4206740875235171802L;

	//系统消息 主要推送在线人数的变化
	public static final String SYS_MSG = "system_message";

	//一般聊天信息
	public static final String COM_MSG = "common_message";
	
	private String type;
	private String date;
	private String username;
	private String message;
	private int count;
	
	public Message() {
	}
	
	public Message(String type, String date, String username, String message, int count) {
		this.type = type;
		this.date = date;
		this.username = username;
		this.message = message;
		this.count = count;
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
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
}
