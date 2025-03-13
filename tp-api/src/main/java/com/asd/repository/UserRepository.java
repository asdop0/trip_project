package com.asd.repository;

import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.asd.model.User;

@Repository
public class UserRepository {
    private final HashOperations<String, Object, Object> hashOperations;


    public UserRepository(RedisTemplate<String, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }
    
	// 유저의 회원정보를 가져옴
	public User getUser(String id) {
		String hashKey = "user:" + id;
		Map<Object, Object> userHashMap = hashOperations.entries(hashKey);
		
		if(userHashMap == null || userHashMap.isEmpty()) {
			throw new IllegalArgumentException("[getUser] 해당 유저를 찾을 수 없습니다.");
		}
		User user = new User(
		        (String) userHashMap.get("id"),
		        (String) userHashMap.get("password"),
		        (String) userHashMap.get("nickname"),
		        (String) userHashMap.get("email"),
		        (String) userHashMap.get("refreshToken"),
		        (String) userHashMap.get("tripCount")
			);
		return user;
    }
	
	// 닉네임에서 아이디 추출
	public String getIdByNickname(String nickname) {
		Object id = hashOperations.get("nicknames", nickname);

	    if (id == null) {
	        throw new IllegalArgumentException("잘못된 닉네임: " + nickname);
	    }
		return id.toString();
    }
	
	// 유저의 여행 일정 작성 횟수 증가
	public void upTripCount(String id, String tripCount) {
		String hashKey = "user:" + id;
		hashOperations.put(hashKey, "tripCount", "0");
	}
}
