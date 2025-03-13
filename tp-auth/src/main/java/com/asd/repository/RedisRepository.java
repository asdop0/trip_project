package com.asd.repository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.asd.model.User;

@Repository
public class RedisRepository {
    private final HashOperations<String, Object, Object> hashOperations;
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;
    }
    
	//회원가입
	public void sginUp(String id, String password, String nickname, String email) {
	    String hashKey = "user:" + id;
	    hashOperations.put(hashKey, "id", id);
	    hashOperations.put(hashKey, "password", password);
	    hashOperations.put(hashKey, "nickname", nickname);
	    hashOperations.put(hashKey, "email", email);
	    hashOperations.put(hashKey, "tripCount", "0");
	    hashOperations.put("nicknames", nickname, id);
	}
	
	//유저의 회원정보를 가져옴
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
	
	//닉네임 목록을 가져옴
	public Set<String> getNicknameList() {
		Map<Object, Object> namehashMap = hashOperations.entries("nicknames");
		
		Set<String> keys = new HashSet<>();
		keys.addAll(namehashMap.keySet().stream().map(String::valueOf).collect(Collectors.toSet()));
		return keys;
    }
	
	//리프레쉬토큰 갱신
	public void setRefreshToken(String id, String token) {
		String key = "User:" + id;
		if(isKeyExistsWithPattern(key)) {
			hashOperations.put(key, "refreshToken", token);
		} else {
			throw new IllegalArgumentException("[setRefreshToken] 해당 유저를 찾을 수 없습니다.");
		}
	}

	public boolean isKeyExistsWithPattern(String id) {	
		String key = "User:" + id;
		Map<Object, Object> result = hashOperations.entries(key);
		if (result.isEmpty()) {
		    return false;
		}
		return true;
	}
	
	//회원탈퇴 (수정 필요) user:id
	public void deleteUser(String id) {
		String pattern = "User:" + id + ":*";
        Set<String> keys = redisTemplate.keys(pattern);
        keys.add("User:" + id);

        if (keys != null && !keys.isEmpty()) {
            // 검색된 모든 키 삭제
        	// 본인이 공유한 여행 일정 및 권한 정보를 공유 받은 사람의 리스트에서 삭제 해야 함
            redisTemplate.delete(keys);
        }
	}
}
