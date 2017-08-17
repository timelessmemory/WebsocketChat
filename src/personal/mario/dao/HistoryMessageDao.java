package personal.mario.dao;

import java.util.List;
import personal.mario.bean.ChatMessage;

public interface HistoryMessageDao {
	public void save(String from, String to, ChatMessage message);
	public List<ChatMessage> get(String from, String to);
}
