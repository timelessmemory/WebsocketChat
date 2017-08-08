package personal.mario.dao;

import java.util.List;

import personal.mario.bean.Message;

public interface MessageDao {
	public void save(Message message);
	public List<Message> getList(String key);
}
