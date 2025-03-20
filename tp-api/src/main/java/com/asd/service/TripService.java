package com.asd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.asd.DTO.TripDto;
import com.asd.model.User;
import com.asd.repository.TripRepository;
import com.asd.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripService {
	private final TripRepository tripRepository;
	private final UserRepository userRepository;
	
	// 여행 목록 조회
	public List<TripDto> getTripList(String userId) {
		List<TripDto> tripList = tripRepository.getTripList(userId);
		
		return tripList;
	}
	
	// 여행 추가
	public void addTrip(String userId, String tripName) {
		User user = userRepository.getUser(userId);
		tripRepository.addTrip(userId, user.getTripCount(), tripName);
		
		int num = Integer.parseInt(user.getTripCount()) + 1;
		userRepository.upTripCount(userId, String.valueOf(num));
	}
	
	// 여행 삭제 
	public void deleteTrip(String userId, String tripCount) {
		tripRepository.deleteTrip(userId, tripCount);
	}
	
	// 여행 이름 변경
	public void changeTripName(String userId, String tripCount, String newTripName) {
		TripDto tripDto = tripRepository.getTrip(userId, tripCount);
		
		tripRepository.changeTripName(userId, tripDto, newTripName);
	}
}
