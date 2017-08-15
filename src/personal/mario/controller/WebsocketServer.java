package personal.mario.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONObject;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.socket.server.standard.SpringConfigurator;
import personal.mario.bean.CopyOnWriteMap;
import personal.mario.bean.HistoryMessageResponse;
import personal.mario.bean.MessageType;
import personal.mario.bean.SystemMessageResponse;
import personal.mario.bean.ChatMessage;
import personal.mario.bean.CommonMessageResponse;
import personal.mario.dao.MessageDao;
import personal.mario.util.CommonMessageEncoder;
import personal.mario.util.SystemMessageEncoder;
import personal.mario.util.HistoryMessageEncoder;

@ServerEndpoint(value="/websocketServer", configurator=SpringConfigurator.class, encoders = { CommonMessageEncoder.class, SystemMessageEncoder.class, HistoryMessageEncoder.class})
public class WebsocketServer {
	//存储每个客户端对应的websocketServer实例与登录名map
    private static CopyOnWriteMap<WebsocketServer, String> webSocketUsernameMap = new CopyOnWriteMap<WebsocketServer, String>();
    
    private MessageDao messageDao = (MessageDao)ContextLoader.getCurrentWebApplicationContext().getBean("messageDao");
    
    //在线成员
    private static ConcurrentLinkedQueue<String> members = new ConcurrentLinkedQueue<String>();

    //每个webscoket客户端与服务器会话
    private Session session;
    
    public WebsocketServer() {
    }
    
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }
    
    @OnClose
    public void onClose() {
        String username = webSocketUsernameMap.get(this);
        removeMember(username);
        webSocketUsernameMap.remove(this);
        
        for (WebsocketServer webSocket : webSocketUsernameMap.keySet()) {
            try {
                sendMsg(webSocket, new SystemMessageResponse(MessageType.SYS_MSG, username, "exit", members));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @OnMessage
    public void onMessage(String message, Session session) {
    	JSONObject messageObject = new JSONObject(message);
    	String type = messageObject.getString("messageType");
    	String content = messageObject.getString("message");
    	
    	if (type.equals(MessageType.COM_MSG)) {
            //群发消息
    		Date time = new Date();
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		
    		for (WebsocketServer item : webSocketUsernameMap.keySet()) {
    			try {
    				sendMsg(item, new CommonMessageResponse(MessageType.COM_MSG, sdf.format(time), webSocketUsernameMap.get(this), content));
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    		ChatMessage msg = new ChatMessage(sdf.format(time), webSocketUsernameMap.get(this), content);
    		messageDao.save(msg);
    	} else if (type.equals(MessageType.SYS_MSG)) {
            //链接成功后客户端会发送登录名  在此进行记录
    		webSocketUsernameMap.put(this, content);
            addMember(content);
            
        	for (WebsocketServer webSocket : webSocketUsernameMap.keySet()) {
                try {
                	sendMsg(webSocket, new SystemMessageResponse(MessageType.SYS_MSG, content, "enter", members));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    	} else {
    		try {
    			List<ChatMessage> historyMessage = messageDao.getList();
            	sendMsg(this, new HistoryMessageResponse(MessageType.HIS_MSG, historyMessage));
            } catch (Exception e) {
                e.printStackTrace();
            }
    	}
    }
    
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    private void sendMsg(WebsocketServer webSocket, Object message) throws Exception {
        webSocket.session.getBasicRemote().sendObject(message);
    }
    
    public static int getMembersCount() {
        return members.size();
    }

    public static void addMember(String member) {
    	members.add(member);
    }

    public static void removeMember(String member) {
        members.remove(member);
    }
}
