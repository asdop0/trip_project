package com.asd.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.asd.DTO.TripDto;
import com.asd.exception.ResourceNotFoundException;

@Repository
public class TripRepository {
    private final HashOperations<String, Object, Object> hashOperations;


    public TripRepository(RedisTemplate<String, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }
    
    // 여행 목록 조회
 	public List<TripDto> getTripList(String id) {
 		String hashKey = "User:" + id + ":trips";
 		Map<Object, Object> hashMap = hashOperations.entries(hashKey);
 		
 		if(hashMap == null || hashMap.isEmpty()) {
 			throw new ResourceNotFoundException("등록된 여행 일정이 없습니다.");
 		}
 		
 		List<TripDto> tripDtos = new ArrayList<>(hashMap.size());
 		
 		// tripCount	tripName:dayCount
 		for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
 		    String field = entry.getKey().toString();
 		    String[] value = entry.getValue().toString().split(":");
 		   tripDtos.add(new TripDto (
 				  field,
 				  value[0],
 				  value[1]
 				   ));
 		}
 		return tripDtos;
     }
 	
 	// 여행 조회
 	public TripDto getTrip(String id, String tripCount) {
 		String hashKey = "User:" + id + ":trips";
 		
 		String[] value = hashOperations.get(hashKey, tripCount).toString().split(":");
 		
 		return new TripDto(tripCount, value[0], value[1]);
 	}
 	
 	// 여행 추가
 	public void addTrip(String id, String tripCount, String tripName) {
 		String hashKey = "User:" + id + ":trips";
 		hashOperations.put(hashKey, tripCount, tripName + ":1");
 	}
 	
 	// 여행 삭제
 	public void deleteTrip(String id, String tripCount) {
 		String hashKey = "User:" + id + ":trips";
 		hashOperations.delete(hashKey, tripCount);
 	}
 	
 	// 여행 이름 변경
 	public void changeTripName(String id, TripDto tripDto, String newTripName) {
 		String hashKey = "User:" + id + ":trips";
 		hashOperations.put(hashKey, tripDto.getTripCount(), newTripName + ":" + tripDto.getDayCount());
 	}
}
