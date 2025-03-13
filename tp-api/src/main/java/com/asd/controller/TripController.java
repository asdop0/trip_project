package com.asd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public List<TripDto> getTripList(@RequestParam String nickname) {
		return tripService.getTripList(nickname);
	}
	
	// 여행 추가
	@PostMapping("/add")
	public Map<String, String> addTrip(@RequestParam String nickname, @RequestParam String tripName) {
		tripService.addTrip(nickname, tripName);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
	
	// 여행 삭제
	@DeleteMapping("/delete")
	public Map<String, String> deleteTrip(@RequestParam String nickname, @RequestParam String tripCount) {
		tripService.deleteTrip(nickname, tripCount);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
	
	// 여행 이름 변경
	@PutMapping("/change")
	public Map<String, String> changeTripName(@RequestParam String nickname, @RequestParam String tripCount, @RequestParam String newTripName) {
		tripService.changeTripName(nickname, tripCount, newTripName);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
}
