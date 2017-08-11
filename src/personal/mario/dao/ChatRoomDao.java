package personal.mario.dao;

import java.util.Set;
import javax.websocket.Session;

public interface ChatRoomDao {
	public void save(String chatroom, Session session, String uname);
	public String get(String chatroom, Session session);
	public void remove(String chatroom, Session session);
	public Set<Session> getKeys(String chatroom);
}