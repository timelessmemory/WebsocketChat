package personal.mario.dao.impl;

import java.util.Set;
import javax.websocket.Session;
import org.springframework.stereotype.Repository;
import personal.mario.bean.CopyOnWriteMap;
import personal.mario.dao.ChatRoomDao;

@Repository("chatRoomDao")
public class ChatRoomDaoImpl implements ChatRoomDao {
	public static final String TAG = "unameWebsocketMap";
	
	private static CopyOnWriteMap<String, CopyOnWriteMap<Session, String>> unameWebsocketMap = new CopyOnWriteMap<String, CopyOnWriteMap<Session, String>>();
	
	@Override
	public void save(String chatroom, Session session, String uname) {
		CopyOnWriteMap<Session, String> cowm = unameWebsocketMap.get(chatroom + TAG);
		
		if (cowm == null) {
			cowm = new CopyOnWriteMap<Session, String>();
		}
		cowm.put(session, uname);
		unameWebsocketMap.put(chatroom + TAG, cowm);
	}
	
	@Override
	public String get(String chatroom, Session session) {
		return unameWebsocketMap.get(chatroom + TAG) == null ? null : unameWebsocketMap.get(chatroom + TAG).get(session);
	}
	
	@Override
	public void remove(String chatroom, Session session) {
		if (unameWebsocketMap.get(chatroom + TAG) != null) {
			unameWebsocketMap.get(chatroom + TAG).remove(session);
		}
	}
	
	public Set<Session> getKeys(String chatroom) {
		return unameWebsocketMap.get(chatroom + TAG) == null ? null : unameWebsocketMap.get(chatroom + TAG).keySet();
	}
}
