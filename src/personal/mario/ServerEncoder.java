package personal.mario;

import javax.websocket.EncodeException;
import javax.websocket.Encoder.Text;
import javax.websocket.EndpointConfig;
import org.json.JSONObject;

public class ServerEncoder implements Text<Message> {
	
    @Override  
    public void destroy() {
    }

    @Override  
    public void init(EndpointConfig arg0) {
    }

    @Override  
    public String encode(Message message) throws EncodeException {
    	JSONObject jsonObject = new JSONObject(message);
    	return jsonObject.toString();
    }
}