package personal.mario.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Repository;
import personal.mario.bean.ChatMessage;
import personal.mario.dao.HistoryMessageDao;

@Repository("historyMessageDao")
public class HistoryMessageDaoImpl implements HistoryMessageDao {
	@Autowired
	private RedisTemplate<String, ChatMessage> redisTemplate;
	
	@Override
	public void save(String from, String to, ChatMessage message) {
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessage.class));
		redisTemplate.afterPropertiesSet();
		
		ListOperations<String, ChatMessage> operations = redisTemplate.opsForList();
		
		String storageName = from + to;
		List<ChatMessage> res = operations.range(storageName, 0, operations.size(storageName) - 1);
		
		if (res == null || res.size() == 0) {
			storageName = to + from;
		}
		
		operations.leftPush(storageName, message);
	}
	
	@Override
	public List<ChatMessage> get(String from, String to) {
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessage.class));
		redisTemplate.afterPropertiesSet();
		
		ListOperations<String, ChatMessage> operations = redisTemplate.opsForList();
		
		String storageName = from + to;
		List<ChatMessage> res = operations.range(storageName, 0, operations.size(storageName) - 1);
		
		if (res == null || res.size() == 0) {
			storageName = to + from;
			res = operations.range(storageName, 0, operations.size(storageName) - 1);
		}
		
		return res;
	}
}
