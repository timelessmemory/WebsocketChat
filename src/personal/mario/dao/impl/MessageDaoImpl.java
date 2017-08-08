package personal.mario.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Repository;
import personal.mario.bean.Message;
import personal.mario.dao.MessageDao;
@Repository("messageDao")
public class MessageDaoImpl implements MessageDao {

	@Autowired
	private RedisTemplate<String, Message> redisTemplate;
	
	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Message> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void save(Message message) {
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Message>(Message.class));
		//初始化bean时运行 序列化类涉及泛型所以配置文件注入不适用
		redisTemplate.afterPropertiesSet();

		ListOperations<String, Message> ops = redisTemplate.opsForList();
		ops.leftPush("chatRecord", message);
	}

	@Override
	public List<Message> getList(String key) {
		ListOperations<String, Message> ops = redisTemplate.opsForList();
		return ops.range("chatRecord", 0, ops.size("chatReocrd"));
	}

}
