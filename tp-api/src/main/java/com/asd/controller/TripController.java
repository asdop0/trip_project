package com.asd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asd.DTO.TripDto;
import com.asd.service.TripService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trip")
public class TripController {
	private final TripService tripService;
	
	// 여행 목록 조회
	@GetMapping("/list")
	public List<TripDto> getTripList(@RequestHeader(value = "X_USER_PAYLOAD", required = false) String userId) {
		return tripService.getTripList(userId);
	}
	
	// 여행 추가
	@PostMapping("/add")
	public Map<String, String> addTrip(@RequestHeader(value = "X_USER_PAYLOAD", required = false) String userId, @RequestParam String tripName) {
		tripService.addTrip(userId, tripName);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
	
	// 여행 삭제
	@DeleteMapping("/delete")
	public Map<String, String> deleteTrip(@RequestHeader(value = "X_USER_PAYLOAD", required = false) String userId, @RequestParam String tripCount) {
		tripService.deleteTrip(userId, tripCount);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
	
	// 여행 이름 변경
	@PutMapping("/change")
	public Map<String, String> changeTripName(@RequestHeader(value = "X_USER_PAYLOAD", required = false) String userId, @RequestParam String tripCount, @RequestParam String newTripName) {
		tripService.changeTripName(userId, tripCount, newTripName);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
}
