package personal.mario.dao.impl;

import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.Session;
import org.springframework.stereotype.Repository;
import personal.mario.bean.CopyOnWriteMap;
import personal.mario.dao.ChatSessionStorageDao;

//存储一对一聊天双方的session
@Repository("chatSessionStorageDao")
public class ChatSessionStorageDaoImpl implements ChatSessionStorageDao {
	private static CopyOnWriteMap<String, CopyOnWriteMap<String, Session>> map = new CopyOnWriteMap<>();
	
	@Override
	public void save(String from, String to, String name, Session session) {
		String storageName = from + to;
		
		CopyOnWriteMap<String, Session> unameSession = map.get(storageName);
		
		if (unameSession == null) {
			storageName = to + from;
			unameSession = map.get(storageName);
		}
		
		if (unameSession == null) {
			unameSession = new CopyOnWriteMap<>();
		}
		
		unameSession.put(name, session);
		map.put(storageName, unameSession);
	}
	
	public Session getSession(String from, String to, String uname) {
		String storageName = from + to;
		
		CopyOnWriteMap<String, Session> unameSession = map.get(storageName);
		
		if (unameSession == null) {
			storageName = to + from;
			unameSession = map.get(storageName);
		}
		
		return unameSession == null ? null : unameSession.get(uname);
	}
	
	public void closeSession(Session session) {
		String key = "";
		String uname = "";
		boolean flag = false;
		
		for (Entry<String, CopyOnWriteMap<String, Session>> entry : map.entrySet()) {
			String tmp = entry.getKey();
			for (Entry<String, Session> ety : entry.getValue().entrySet()) {
				if (ety.getValue() == session) {
					uname = ety.getKey();
					key = tmp;
					if (map.get(tmp).size() == 1) {
						flag = true;
					}
				}
			}
		}
		
		if (flag) {
			map.remove(key);
		} else {
			map.get(key).remove(uname);
		}
	}
}
