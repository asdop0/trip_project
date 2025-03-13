package com.asd.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DayRepository {
	private final HashOperations<String, Object, Object> hashOperations;


    public DayRepository(RedisTemplate<String, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }
	
	// 일차 변경
    public void changeDay(String id, String tripCount, String newValue) {
    	String hashKey = "User:" + id + ":trips";
        hashOperations.put(hashKey, tripCount, newValue);
    }				
			
	// 다른 일차 불러오기
}
