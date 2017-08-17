package personal.mario.dao.impl;

import java.util.Set;
import javax.websocket.Session;
import org.springframework.stereotype.Repository;
import personal.mario.bean.CopyOnWriteMap;
import personal.mario.dao.ChatRoomDao;

//存储所有在线session
@Repository("chatRoomDao")
public class ChatRoomDaoImpl implements ChatRoomDao {
	
	private static CopyOnWriteMap<Session, String> sessionUnameMap = new CopyOnWriteMap<Session, String>();
	private static CopyOnWriteMap<String, Session> unameSessionMap = new CopyOnWriteMap<String, Session>();
	
	@Override
	public void save(Session session, String uname) {
		sessionUnameMap.put(session, uname);
		unameSessionMap.put(uname, session);
	}
	
	@Override
	public String getUname(Session session) {
		return sessionUnameMap.get(session);
	}
	
	@Override
	public void remove(Session session) {
		String uname = sessionUnameMap.get(session);
		sessionUnameMap.remove(session);
		unameSessionMap.remove(uname);
	}
	
	public Set<Session> getSessionKeys() {
		return sessionUnameMap.keySet();
	}
	
	public Session getSession(String uname) {
		return unameSessionMap.get(uname);
	}
}