package personal.mario.bean;

import java.util.List;

public class HistoryMessageResponse {
	private String type;
	private List<ChatMessage> content;
	
	public HistoryMessageResponse(){}
	public HistoryMessageResponse(String type, List<ChatMessage> content) {
		this.type = type;
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<ChatMessage> getContent() {
		return content;
	}
	public void setContent(List<ChatMessage> content) {
		this.content = content;
	}
}
