package personal.mario.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import personal.mario.controller.WebsocketServer;
import personal.mario.dao.ChatroomMemberDao;

@Repository("chatroomMemberDao")
public class ChatroomMemberDaoImpl implements ChatroomMemberDao {
	public static final String TAG = "members";
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public void save(String chatroom, String uname) {
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		
		ListOperations<String, String> operations = redisTemplate.opsForList();
		operations.leftPush(chatroom + TAG, uname);
	}
	
	@Override
	public void remove(String chatroom, String uname) {
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		
		ListOperations<String, String> operations = redisTemplate.opsForList();
		operations.remove(chatroom + TAG, 1, uname);
	}
	
	@Override
	public List<String> getAll(String chatroom) {
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		
		ListOperations<String, String> operations = redisTemplate.opsForList();
		return operations.range(chatroom + TAG, 0, operations.size(chatroom + TAG) - 1);
	}
}
