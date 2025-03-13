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
	public List<TripDto> getTripList(String nickname) {
		// nickname -> id 변환
		String id = userRepository.getIdByNickname(nickname);
		
		List<TripDto> tripList = tripRepository.getTripList(id);
		
		return tripList;
	}
	
	// 여행 추가
	public void addTrip(String nickname, String tripName) {
		// nickname -> id 변환
		String id = userRepository.getIdByNickname(nickname);
		
		User user = userRepository.getUser(id);
		tripRepository.addTrip(id, user.getTripCount(), tripName);
		
		int num = Integer.parseInt(user.getTripCount()) + 1;
		userRepository.upTripCount(id, String.valueOf(num));
	}
	
	// 여행 삭제 
	public void deleteTrip(String nickname, String tripCount) {
		// nickname -> id 변환
		String id = userRepository.getIdByNickname(nickname);
		
		tripRepository.deleteTrip(id, tripCount);
	}
	
	// 여행 이름 변경
	public void changeTripName(String nickname, String tripCount, String newTripName) {
		// nickname -> id 변환
		String id = userRepository.getIdByNickname(nickname);
		
		TripDto tripDto = tripRepository.getTrip(id, tripCount);
		
		tripRepository.changeTripName(id, tripDto, newTripName);
	}
}
