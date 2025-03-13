package com.asd.service;

import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.asd.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{
	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public UserDetails loadUserByUsername(String key) throws IllegalArgumentException {
		Map<Object, Object> userHashMap = redisTemplate.opsForHash().entries(key);
		
		if(userHashMap.isEmpty()) {
			throw new IllegalArgumentException("[loadUserByUsername] 해당 유저를 찾을 수 없습니다.");
		}
		
		UserDetails userDetails = new User(
	        (String) userHashMap.get("id"),
	        (String) userHashMap.get("password"),
	        (String) userHashMap.get("nickname"),
	        (String) userHashMap.get("email"),
	        (String) userHashMap.get("refreshToken"),
	        (String) userHashMap.get("tripCount")
	        );
		return userDetails;
	}
	
}
