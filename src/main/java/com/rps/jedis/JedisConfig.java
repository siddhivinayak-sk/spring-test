package com.rps.jedis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class JedisConfig {

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jcf = new JedisConnectionFactory();
		jcf.setHostName("localhost");
		jcf.setPort(6000);
		jcf.setUsePool(true);
		jcf.afterPropertiesSet();
		return jcf;
	}
	

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> rt = new RedisTemplate<String, Object>();
		rt.setConnectionFactory(jedisConnectionFactory());
		rt.setKeySerializer(new StringRedisSerializer());
		rt.setValueSerializer(new StringRedisSerializer());
		rt.setHashKeySerializer(new StringRedisSerializer());
		rt.setHashValueSerializer(new StringRedisSerializer());
		rt.afterPropertiesSet();
		return rt;
	}

	
	@Bean
	public Topic topic() {
		return new ChannelTopic("topic-x");
	}
	
	@Bean
	public RedisMessageListenerContainer redisContainer() {
		RedisMessageListenerContainer c = new RedisMessageListenerContainer();
		c.setConnectionFactory(jedisConnectionFactory());
		c.addMessageListener(new MessageListener() {
			public void onMessage(org.springframework.data.redis.connection.Message message, byte[] data) {
				System.out.println("Received on Jedis Queue : " + message);
			}
		}, topic());
		return c;
	}
	
}
