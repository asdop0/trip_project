package com.asd.repository;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

@Repository
public class SharingRepository {
    private final SetOperations<String, Object> setOperations;

    public SharingRepository(RedisTemplate<String, Object> redisTemplate) {
        this.setOperations = redisTemplate.opsForSet();
    }
    
    // 특정 여행에 대한 권한 추가(받은 사람 닉네임:여행 이름:권한)
    public void addSendTrip(String senderId, String receiver, String trip, String auth) {
        String setKey = "user:" + senderId + ":trip:" + trip + ":send";
        setOperations.add(setKey, receiver + ":" + trip + ":" + auth);
    }

    // 공유받은 여행 추가(보낸 사람 닉네임:여행 이름:권한)
    public void addReceiveTrip(String receiverId, String sender, String trip, String auth) {
        String setKey = "user:" + receiverId + ":receive";
        setOperations.add(setKey, sender + ":" + trip + ":" + auth);
    }

    // 특정 여행에 대한 권한 삭제
    public Long removeSendTrip(String senderId, String receiver, String trip, String auth) {
        String setKey = "user:" + senderId + ":trip:" + trip + ":send";
        return setOperations.remove(setKey, receiver + ":" + trip + ":" + auth);
    }

    // 공유받은 여행 삭제
    public Long removeReceiveTrip(String receiverId, String sender, String trip, String auth) {
        String setKey = "user:" + receiverId + ":receive";
        return setOperations.remove(setKey, sender + ":" + trip + ":" + auth);
    }

    // 특정 여행에 대한 권한 출력
    public Set<String> getSendTrips(String id, String trip) {
        String setKey = "user:" + id + ":trip:" + trip + ":send";
        return setOperations.members(setKey).stream().map(Object::toString).collect(Collectors.toSet());
    }

    // 공유받은 여행 출력
    public Set<String> getReceivedTrips(String id) {
        String setKey = "user:" + id + ":receive";
        return setOperations.members(setKey).stream().map(Object::toString).collect(Collectors.toSet());
    }
}
