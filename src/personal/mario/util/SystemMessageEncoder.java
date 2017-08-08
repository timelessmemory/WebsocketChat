package personal.mario.util;

import javax.websocket.EncodeException;
import javax.websocket.Encoder.Text;

import org.json.JSONObject;

import javax.websocket.EndpointConfig;
import personal.mario.bean.SystemMessageResponse;

public class SystemMessageEncoder implements Text<SystemMessageResponse> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(SystemMessageResponse message) throws EncodeException {
		JSONObject jsonObject = new JSONObject(message);
    	return jsonObject.toString();
	}
}
