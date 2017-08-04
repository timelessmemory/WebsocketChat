package personal.mario;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;
import org.springframework.web.socket.server.standard.SpringConfigurator;

@ServerEndpoint(value="/websocketServer", configurator=SpringConfigurator.class, encoders = { ServerEncoder.class })
public class WebsocketServer {
	//存储每个客户端对应的websocketServer实例与登录名map
    private static CopyOnWriteMap<WebsocketServer, String> webSocketUsernameMap = new CopyOnWriteMap<WebsocketServer, String>();
    
    //在线人数
    private static int onlineCount = 0;

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
    	webSocketUsernameMap.remove(this);
        removeOnlineCount();
        
        for (WebsocketServer webSocket : webSocketUsernameMap.keySet()) {
            try {
            	sendMsg(webSocket, new Message(Message.SYS_MSG, null, "", "", getOnlineCount()));
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
    	
    	if (type.equals(Message.COM_MSG)) {
            //群发消息
    		Date time = new Date();
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		
    		for (WebsocketServer item : webSocketUsernameMap.keySet()) {
    			try {
    				sendMsg(item, new Message(Message.COM_MSG, sdf.format(time), webSocketUsernameMap.get(item), content, getOnlineCount()));
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	} else {
            //链接成功后客户端会发送登录名  在此进行记录
    		webSocketUsernameMap.put(this, content);
            addOnlineCount();
            
        	for (WebsocketServer webSocket : webSocketUsernameMap.keySet()) {
                try {
                	sendMsg(webSocket, new Message(Message.SYS_MSG, null, "", "", getOnlineCount()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    	}
    }
    
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    private void sendMsg(WebsocketServer webSocket, Message message) throws Exception {
        webSocket.session.getBasicRemote().sendObject(message);
    }
    
    public static int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebsocketServer.onlineCount++;
    }

    public static synchronized void removeOnlineCount() {
        WebsocketServer.onlineCount--;
    }
}
