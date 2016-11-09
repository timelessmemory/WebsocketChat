package personal.mario;

import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.web.socket.server.standard.SpringConfigurator;

@ServerEndpoint(value="/websocket", configurator=SpringConfigurator.class, encoders = { ServerEncoder.class })
public class MyWebSocket {
	
    private static int onlineCount = 0;
    
    //store all client object which is instance of MyWebSocket
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
    private Session session;
    
    public MyWebSocket() {
    }
    
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        
        //notice onlineCount to client
    	for (MyWebSocket webSocket : webSocketSet) {
            try {
            	webSocket.session.getBasicRemote().sendObject(new Message("", getOnlineCount()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        removeOnlineCount();
        
        for (MyWebSocket webSocket : webSocketSet) {
            try {
            	webSocket.session.getBasicRemote().sendObject(new Message("", getOnlineCount()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @OnMessage
    public void onMessage(String message, Session session) {
    	
        //send message to everyone
        for (MyWebSocket item : webSocketSet) {
            try {
                item.session.getAsyncRemote().sendObject(new Message(message, getOnlineCount()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
    
    public static int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void removeOnlineCount() {
        MyWebSocket.onlineCount--;
    }
}
