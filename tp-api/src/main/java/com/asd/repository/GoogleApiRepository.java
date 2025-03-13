package com.osj.repository;

import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.osj.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Set;

@Repository
public class GoogleApiRepository {

    private final ZSetOperations<String, String> zSetOperations;
    private final ListOperations<String, String> listOperations;
    private final RedisTemplate<String, String> redisTemplate;
    
    public GoogleApiRepository(RedisTemplate<String, String> redisTemplate) {
        this.zSetOperations = redisTemplate.opsForZSet();
        this.listOperations = redisTemplate.opsForList();
        this.redisTemplate = redisTemplate;
    }

    // 날짜별 장소 저장 (List에 추가)
    public void savePlace(String key, String placeJson) {
        listOperations.rightPush(key, placeJson);  // List 끝에 장소 추가
    }

    // 날짜별 장소 조회 (List 반환)
    public List<String> getPlaces(String key) {
        List<String> result = listOperations.range(key, 0, -1);

        if (result == null || result.isEmpty()) {
            throw new ResourceNotFoundException("저장된 장소가 없습니다.");
        }

        return result; // List 내 모든 항목 조회
     }
    
    // 날짜별 장소 변경
    public void updatePlacesOrder(String key, List<String> updatedPlaces) {
        // 기존 리스트를 비우기 위해 LTRIM을 사용하여 리스트를 초기화
        listOperations.trim(key, 1, 0);

        // 새로운 리스트를 순서대로 저장
        for (String place : updatedPlaces) {
            listOperations.rightPush(key, place);
        }
    }

    // 날짜별 장소 삭제 (List에서 해당 항목 삭제)
    public void deletePlace(String key, String placeJson) {
        listOperations.remove(key, 0, placeJson);  // 해당 장소를 List에서 삭제
    }

    // 날짜별 장소 전체 삭제 (List 내 모든 항목 삭제)
    public void deleteAllPlaces(String key) {
        redisTemplate.delete(key);  // 해당 key에 저장된 모든 항목 삭제
    }

    // 날짜별 길찾기 결과 저장 (Sorted Set에 추가)
    public void saveDirections(String key, String directionsResult, double score) {
        zSetOperations.add(key + ":directions", directionsResult, score);  // Sorted Set에 결과 추가
    }

    // 날짜별 길찾기 결과 조회 (Sorted Set 내 모든 항목 조회)
    public Set<String> getDirections(String key) {
        Set<String> result = zSetOperations.range(key + ":directions", 0, -1);

        if (result == null || result.isEmpty()) {
            throw new ResourceNotFoundException("저장된 길찾기 정보가 없습니다.");
        }

        return result; // Sorted Set 내 모든 길찾기 결과 조회
     }

    // 특정 길찾기 결과 삭제 (Sorted Set에서 해당 항목 삭제)
    public void deleteDirection(String key, String directionsResult) {
        zSetOperations.remove(key + ":directions", directionsResult);  // Sorted Set에서 해당 데이터 삭제
    }

    // 특정 날짜의 모든 길찾기 결과 삭제 (Sorted Set 내 모든 항목 삭제)
    public void deleteAllDirections(String key) {
        redisTemplate.delete(key + ":directions");  // 전체 삭제
    }
}
