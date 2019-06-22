package com.rps.controllers;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jedis")
public class JedisControllerTest {

	@Autowired
	RedisTemplate redisTemplate;
	
	@GetMapping("/set/{key}/{value}")
	public String setKeyValue(@PathVariable("key")String key, @PathVariable("value")String value) {
		redisTemplate.opsForValue().set(key, value);
		redisTemplate.expire(key, 20, TimeUnit.SECONDS);
		return key;
	}
	
	@GetMapping("/get/{key}")
	public String getKeyValue(@PathVariable("key")String key) {
		return (String) redisTemplate.opsForValue().get(key);
	}
	
	
	@GetMapping("/set1/{key}")
	public String setKey1(@PathVariable("key")String key) {
		Properties p = new Properties();
		p.put("A", "1");
		p.put("B", "2");
		p.put("C", "3");
		redisTemplate.opsForHash().putAll(key, p);
		redisTemplate.expire(key, 20, TimeUnit.SECONDS);
		return key;
	}
	
	@GetMapping("/get1/{key}")
	public String getKey1(@PathVariable("key")String key) {
		return (String) redisTemplate.opsForHash().get(key, "A") + " " + (String) redisTemplate.opsForHash().get(key, "B");
	}
	
	
	
	@GetMapping("push")
	public String pushMessage(@RequestParam("message")String message) {
		redisTemplate.execute(new RedisCallback<Long>(){
			public Long doInRedis(RedisConnection connection) {
				return connection.publish(redisTemplate.getKeySerializer().serialize("topic-x"), redisTemplate.getValueSerializer().serialize(message));
			}
		});
		return "sent!";
	}
	
	
	
}
