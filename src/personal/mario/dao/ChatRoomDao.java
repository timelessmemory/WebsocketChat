package personal.mario.dao;

import java.util.Set;
import javax.websocket.Session;

public interface ChatRoomDao {
	public void save(Session session, String uname);
	public String getUname(Session session);
	public void remove(Session session);
	public Set<Session> getSessionKeys();
	public Session getSession(String uname);
}