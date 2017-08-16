package personal.mario.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONObject;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import personal.mario.bean.CommonMessageResponse;
import personal.mario.bean.MessageType;
import personal.mario.bean.SystemMessageResponse;
import personal.mario.dao.ChatRoomDao;
import personal.mario.dao.ChatSessionStorageDao;
import personal.mario.dao.ChatroomMemberDao;
import personal.mario.dao.impl.ChatSessionStorageDaoImpl;
import personal.mario.util.CommonMessageEncoder;
import personal.mario.util.SystemMessageEncoder;

@ServerEndpoint(value="/chatServer", configurator=SpringConfigurator.class, encoders = {CommonMessageEncoder.class})
public class ChatServer {
	private ChatRoomDao chatRoomDao = (ChatRoomDao)ContextLoader.getCurrentWebApplicationContext().getBean("chatRoomDao");
    private ChatSessionStorageDao chatSessionStorageDao = (ChatSessionStorageDao)ContextLoader.getCurrentWebApplicationContext().getBean("chatSessionStorageDao");

    public ChatServer() {
    }
    
    @OnOpen
    public void onOpen(Session session) {
    }
    
    @OnClose
    public void onClose(Session session) {
        
    }
    
    @OnMessage
    public void onMessage(String message, Session session) {
    	JSONObject messageObject = new JSONObject(message);
    	String messageType = messageObject.getString("messageType");
    	String from = messageObject.getString("from");
    	String to = messageObject.getString("to");
    	
        if (messageType.equals(MessageType.SYS_MSG)) {
            chatSessionStorageDao.save(from, to, from, session);
        } else if (messageType.equals(MessageType.COM_MSG)) {
        	String sendMessage = messageObject.getString("message");
        	Session toSession = chatSessionStorageDao.getSession(from, to, to);
        	
        	if (toSession == null) {
        		toSession = chatRoomDao.getSession(to);
        	}
        	
        	Date time = new Date();
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	
        	try {
				sendMsg(toSession, new CommonMessageResponse(MessageType.COM_MSG, sdf.format(time), from, sendMessage));
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
    
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    private void sendMsg(Session session, Object message) throws Exception {
        session.getBasicRemote().sendObject(message);
    }
}
