package personal.mario.util;

import javax.websocket.EncodeException;
import javax.websocket.Encoder.Text;
import javax.websocket.EndpointConfig;
import org.json.JSONObject;

import personal.mario.bean.CommonMessageResponse;

public class CommonMessageEncoder implements Text<CommonMessageResponse> {
	
    @Override  
    public void destroy() {
    }

    @Override  
    public void init(EndpointConfig arg0) {
    }

    @Override  //转换websocket send方法中的CommonMessageResponse对象为json字符串
    public String encode(CommonMessageResponse message) throws EncodeException {
    	JSONObject jsonObject = new JSONObject(message);
    	return jsonObject.toString();
    }
}