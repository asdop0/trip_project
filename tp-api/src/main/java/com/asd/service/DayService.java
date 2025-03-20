package com.asd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.asd.DTO.TripDto;
import com.asd.repository.DayRepository;
import com.asd.repository.GoogleApiRepository;
import com.asd.repository.TripRepository;
import com.asd.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DayService {
	private final DayRepository dayRepository;
	private final UserRepository userRepository;
	private final TripRepository tripRepository;
	private final GoogleApiRepository googleApiRepository;
	
	// 일차 변경
	public void changeDay(String userId, String tripCount, int num) {	
		TripDto tripDto = tripRepository.getTrip(userId, tripCount);

        int count = Integer.parseInt(tripDto.getDayCount()) + num;
		if(count != 0) {
			dayRepository.changeDay(userId, tripDto.getTripCount(), tripDto.getTripName() + ":" + count);
		}
		
		if(num == -1) {
			googleApiRepository.deleteAllPlaces(userId, tripDto.getTripCount(), tripDto.getDayCount());
			googleApiRepository.deleteAllDirections(userId, tripDto.getTripCount(), tripDto.getDayCount());
		}
	}		
		
	// 특정 일자 초기화
	public void clearDay(String userId, String tripCount, String dayCount) {
		googleApiRepository.deleteAllPlaces(userId);
		googleApiRepository.deleteAllDirections(userId, tripCount, dayCount);
	}
		
	// 다른 일차 불러오기
	public void overwriteDay(String userId, String tripCount, String loadDayCount, String applyDayCount) {
		// 기존 정보 삭제
		googleApiRepository.deleteAllPlaces(userId, tripCount, applyDayCount);
		googleApiRepository.deleteAllDirections(userId, tripCount, applyDayCount);
		
		List<String> placeList = googleApiRepository.getPlaces(userId, tripCount, loadDayCount);
		for(String place : placeList) {
			googleApiRepository.savePlace(userId, tripCount, applyDayCount, place);
		}
		
		// 
		List<String> directionList = googleApiRepository.getDirections(userId, tripCount, loadDayCount);
		for(String direction : directionList) {
			googleApiRepository.savesaveDirections(userId, tripCount, applyDayCount, direction);
		}
	}
}
