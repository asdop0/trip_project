package com.asd.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asd.service.DayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/day")
public class DayContorpller {
	private final DayService dayService;
	
	// 일차 변경
	@PutMapping("/changeDay")
	public Map<String, String> addDay(@RequestParam String nickname, @RequestParam String tripCount, @RequestParam int num) {
		dayService.changeDay(nickname, tripCount, num);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}

	// 특정 일자 초기화
	@DeleteMapping("/clearDay")
	public Map<String, String> clearDay(@RequestParam String nickname, @RequestParam String tripCount, @RequestParam String dayCount) {
		dayService.clearDay(nickname, tripCount, dayCount);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
	
	// 다른 일차 불러오기
	@PutMapping("/overwriteDay")
	public Map<String, String> overwriteDay(@RequestParam String nickname, @RequestParam String tripCount, @RequestParam String loadDayCount, @RequestParam String applyDayCount) {
		dayService.overwriteDay(nickname, tripCount, loadDayCount, applyDayCount);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
}
