package personal.mario.dao.impl;

import java.util.Set;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;
import personal.mario.bean.ChatMessage;
import personal.mario.dao.ChatRoomDao;

@Repository("chatRoomDao")
public class ChatRoomDaoImpl implements ChatRoomDao {
	public static final String TAG = "unameWebsocketMap";
	
	@Autowired
	private RedisTemplate<String, String> unameWebsocketMap;
	
	@Override
	public void save(String chatroom, Session session, String uname) {
		unameWebsocketMap.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(Session.class));
		unameWebsocketMap.setHashValueSerializer(new StringRedisSerializer());
		unameWebsocketMap.afterPropertiesSet();

		HashOperations<String, Session, String> ops = unameWebsocketMap.opsForHash();
		ops.put(chatroom + TAG, session, uname);
	}
	
	@Override
	public String get(String chatroom, Session session) {
		unameWebsocketMap.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(Session.class));
		unameWebsocketMap.setHashValueSerializer(new StringRedisSerializer());
		unameWebsocketMap.afterPropertiesSet();
		
		HashOperations<String, Session, String> ops = unameWebsocketMap.opsForHash();
		return ops.get(chatroom + TAG, session);
	}
	
	@Override
	public void remove(String chatroom, Session session) {
		unameWebsocketMap.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(Session.class));
		unameWebsocketMap.setHashValueSerializer(new StringRedisSerializer());
		unameWebsocketMap.afterPropertiesSet();
		
		HashOperations<String, Session, String> ops = unameWebsocketMap.opsForHash();
		ops.delete(chatroom + TAG, session);
	}
	
	public Set<Session> getKeys(String chatroom) {
		unameWebsocketMap.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(Session.class));
		unameWebsocketMap.setHashValueSerializer(new StringRedisSerializer());
		unameWebsocketMap.afterPropertiesSet();
		
		HashOperations<String, Session, String> ops = unameWebsocketMap.opsForHash();
		return ops.keys(chatroom + TAG);
	}
}
