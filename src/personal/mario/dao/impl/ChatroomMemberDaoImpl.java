package personal.mario.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;
import personal.mario.dao.ChatroomMemberDao;

@Repository("chatroomMemberDao")
public class ChatroomMemberDaoImpl implements ChatroomMemberDao {
	public static final String TAG = "members";
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public void save(String uname) {
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		
		ListOperations<String, String> operations = redisTemplate.opsForList();
		operations.leftPush(TAG, uname);
	}
	
	@Override
	public void remove(String uname) {
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		
		ListOperations<String, String> operations = redisTemplate.opsForList();
		operations.remove(TAG, 1, uname);
	}
	
	@Override
	public List<String> getAll() {
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		
		ListOperations<String, String> operations = redisTemplate.opsForList();
		return operations.range(TAG, 0, operations.size(TAG) - 1);
	}
}
