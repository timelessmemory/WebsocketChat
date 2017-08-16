package personal.mario.dao;

import javax.websocket.Session;

public interface ChatSessionStorageDao {
	public void save(String from, String to, String name, Session session);
	public Session getSession(String from, String to, String uname);
}
