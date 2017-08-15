package personal.mario.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONObject;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.socket.server.standard.SpringConfigurator;
import personal.mario.bean.HistoryMessageResponse;
import personal.mario.bean.MessageType;
import personal.mario.bean.SystemMessageResponse;
import personal.mario.bean.ChatMessage;
import personal.mario.bean.CommonMessageResponse;
import personal.mario.dao.ChatRoomDao;
import personal.mario.dao.ChatroomMemberDao;
import personal.mario.dao.MessageDao;
import personal.mario.util.CommonMessageEncoder;
import personal.mario.util.SystemMessageEncoder;
import personal.mario.util.HistoryMessageEncoder;

@ServerEndpoint(value="/websocketServer/{chatRoomId}", configurator=SpringConfigurator.class, encoders = { CommonMessageEncoder.class, SystemMessageEncoder.class, HistoryMessageEncoder.class})
public class WebsocketServer {
	private ChatRoomDao chatRoomDao = (ChatRoomDao)ContextLoader.getCurrentWebApplicationContext().getBean("chatRoomDao");
    private MessageDao messageDao = (MessageDao)ContextLoader.getCurrentWebApplicationContext().getBean("messageDao");
    private ChatroomMemberDao chatroomMemberDao = (ChatroomMemberDao)ContextLoader.getCurrentWebApplicationContext().getBean("chatroomMemberDao");

//    private Session session;
    
    public WebsocketServer() {
    }
    
    @OnOpen
    public void onOpen(Session session) {
//        this.session = session;
    }
    
    @OnClose
    public void onClose(Session session, @PathParam("chatRoomId") String chatRoomId) {
        String username = chatRoomDao.get(chatRoomId, session);
        chatroomMemberDao.remove(chatRoomId, username);
        chatRoomDao.remove(chatRoomId, session);
        
        for (Session sesion : chatRoomDao.getKeys(chatRoomId)) {
            try {
                sendMsg(sesion, new SystemMessageResponse(MessageType.SYS_MSG, username, "exit", chatroomMemberDao.getAll(chatRoomId)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("chatRoomId") String chatRoomId) {
    	JSONObject messageObject = new JSONObject(message);
    	String type = messageObject.getString("messageType");
    	String content = messageObject.getString("message");
    	
    	if (type.equals(MessageType.COM_MSG)) {
            //群发消息
    		Date time = new Date();
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		
    		for (Session sesion : chatRoomDao.getKeys(chatRoomId)) {
    			try {
    				sendMsg(sesion, new CommonMessageResponse(MessageType.COM_MSG, sdf.format(time), chatRoomDao.get(chatRoomId, session), content));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
			ChatMessage msg = new ChatMessage(sdf.format(time), chatRoomDao.get(chatRoomId, session), content);
			messageDao.save(chatRoomId, msg);
    	} else if (type.equals(MessageType.SYS_MSG)) {
    		chatRoomDao.save(chatRoomId, session, content);
            chatroomMemberDao.save(chatRoomId, content);
            
        	for (Session sesion : chatRoomDao.getKeys(chatRoomId)) {
                try {
                	sendMsg(sesion, new SystemMessageResponse(MessageType.SYS_MSG, content, "enter", chatroomMemberDao.getAll(chatRoomId)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    	} else {
    		try {
    			List<ChatMessage> historyMessage = messageDao.getList(chatRoomId);
            	sendMsg(session, new HistoryMessageResponse(MessageType.HIS_MSG, historyMessage));
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
