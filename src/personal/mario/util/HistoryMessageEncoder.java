package personal.mario.util;

import javax.websocket.EncodeException;
import javax.websocket.Encoder.Text;

import org.json.JSONObject;

import javax.websocket.EndpointConfig;

import personal.mario.bean.HistoryMessageResponse;

public class HistoryMessageEncoder implements Text<HistoryMessageResponse> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(HistoryMessageResponse message) throws EncodeException {
		JSONObject jsonObject = new JSONObject(message);
    	return jsonObject.toString();
	}

}
