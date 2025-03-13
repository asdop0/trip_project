package com.osj.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osj.DTO.PlaceDto;
import com.osj.exception.ResourceNotFoundException;
import com.osj.repository.GoogleApiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleApiService {

    private final GoogleApiRepository googleApiRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 장소 저장
    public void savePlace(String key, String placeJson) {
        googleApiRepository.savePlace(key, placeJson);
    }

    // 장소 조회
    public List<PlaceDto> getPlaces(String key) {
         List<String> places = googleApiRepository.getPlaces(key);
         
         return places.stream()
        		 .map(json -> {
        			 try {
        				 return objectMapper.readValue(json, PlaceDto.class);
        			 } catch (JsonProcessingException e) {
        				 throw new RuntimeException("JSON 파싱 오류", e);
        			 }
        		 }).collect(Collectors.toList());
    }

    // 장소 순서 변경
    public void updatePlacesOrder(String key, List<String> updatedPlaces) {
        googleApiRepository.updatePlacesOrder(key, updatedPlaces);
    }

    // 특정 장소 삭제
    public void deletePlace(String key, String name) {
        List<String> places = googleApiRepository.getPlaces(key);
        for (String place : places) {
            if (place.contains(name)) {
                googleApiRepository.deletePlace(key, place);
                return;
            }
        }
        throw new ResourceNotFoundException("Error: Place not found!");
    }

    // 전체 장소 삭제
    public void deleteAllPlaces(String key) {
        googleApiRepository.deleteAllPlaces(key);
    }

    // 길찾기 결과 저장
    public void saveDirections(String key, String directionsResult, double score) {
        googleApiRepository.saveDirections(key, directionsResult, score);
    }

    // 길찾기 결과 조회
    public Set<String> getDirections(String key) {
        return googleApiRepository.getDirections(key);
    }

    // 특정 길찾기 결과 삭제
    public void deleteDirection(String key, String route) {
        googleApiRepository.deleteDirection(key, route);
    }

    // 모든 길찾기 결과 삭제
    public void deleteAllDirections(String key) {
        googleApiRepository.deleteAllDirections(key);
    }
}
