package personal.mario.dao;

import java.util.List;
import personal.mario.bean.ChatMessage;

public interface MessageDao {
	public void save(ChatMessage message);
	public List<ChatMessage> getList();
}
