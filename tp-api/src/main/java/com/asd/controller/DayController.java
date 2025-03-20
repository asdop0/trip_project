package com.asd.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asd.service.DayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/day")
public class DayController {
	private final DayService dayService;
	
	// 일차 변경
	@PutMapping("/changeDay")
	public Map<String, String> addDay(@RequestHeader(value = "X_USER_PAYLOAD", required = false) String userId, @RequestParam String tripCount, @RequestParam int num) {
		dayService.changeDay(userId, tripCount, num);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}

	// 특정 일자 초기화
	@DeleteMapping("/clearDay")
	public Map<String, String> clearDay(@RequestHeader(value = "X_USER_PAYLOAD", required = false) String userId, @RequestParam String tripCount, @RequestParam String dayCount) {
		dayService.clearDay(userId, tripCount, dayCount);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
	
	// 다른 일차 불러오기
	@PutMapping("/overwriteDay")
	public Map<String, String> overwriteDay(@RequestHeader(value = "X_USER_PAYLOAD", required = false) String userId, @RequestParam String tripCount, @RequestParam String loadDayCount, @RequestParam String applyDayCount) {
		dayService.overwriteDay(userId, tripCount, loadDayCount, applyDayCount);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
}
