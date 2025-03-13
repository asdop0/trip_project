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
	public void changeDay(String nickname, String tripCount, int num) {
		String id = userRepository.getIdByNickname(nickname);
		
		TripDto tripDto = tripRepository.getTrip(id, tripCount);

        int count = Integer.parseInt(tripDto.getDayCount()) + num;
		if(count != 0) {
			dayRepository.changeDay(id, tripDto.getTripCount(), tripDto.getTripName() + ":" + count);
		}
		
		if(num == -1) {
			googleApiRepository.deleteAllPlaces(id, tripDto.getTripCount(), tripDto.getDayCount());
			googleApiRepository.deleteAllDirections(id, tripDto.getTripCount(), tripDto.getDayCount());
		}
	}		
		
	// 특정 일자 초기화
	public void clearDay(String nickname, String tripCount, String dayCount) {
		String id = userRepository.getIdByNickname(nickname);
		
		googleApiRepository.deleteAllPlaces(id, tripCount, dayCount);
		googleApiRepository.deleteAllDirections(id, tripCount, dayCount);
	}
		
	// 다른 일차 불러오기
	public void overwriteDay(String nickname, String tripCount, String loadDayCount, String applyDayCount) {
		String id = userRepository.getIdByNickname(nickname);
		
		// 기존 정보 삭제
		googleApiRepository.deleteAllPlaces(id, tripCount, applyDayCount);
		googleApiRepository.deleteAllDirections(id, tripCount, applyDayCount);
		
		List<String> placeList = googleApiRepository.getPlaces(id, tripCount, loadDayCount);
		for(String place : placeList) {
			googleApiRepository.savePlace(id, tripCount, applyDayCount, place);
		}
		
		// 
		List<String> directionList = googleApiRepository.getDirections(id, tripCount, loadDayCount);
		for(String direction : directionList) {
			googleApiRepository.savesaveDirections(id, tripCount, applyDayCount, direction);
		}
	}
}
