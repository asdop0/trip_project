package com.asd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asd.DTO.SharingDto;
import com.asd.service.SharingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sharing")
public class SharingController {
	private final SharingService sharingService;
	
	// 자신의 여행을 남에게 공유하기
	@PostMapping("/grant")
	public Map<String, String> sharingMyTrip(@RequestHeader(value = "X_USER_PAYLOAD", required = false) String userId, @RequestBody SharingDto requestDto) {
		sharingService.sharingMyTrip(userId, requestDto.getReceiver(), requestDto.getTrip(), requestDto.getAuth());
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
	
	// 공유된 여행 권한 삭제
	@DeleteMapping("/delete")
	public Map<String, String> deleteAuth(@RequestBody SharingDto requestDto) {
		sharingService.deleteAuth(requestDto.getSender(), requestDto.getReceiver(), requestDto.getTrip(), requestDto.getAuth());
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
	
	// 해당 여행의 권한 목록
	@GetMapping("/getAuth")
	public List<SharingDto> getAuth(@RequestHeader(value = "X_USER_PAYLOAD", required = false) String userId, @RequestParam String tripCount) {
		return sharingService.getAuth(userId, tripCount);
	}
	
	// 자신이 공유 받은 여행 목록
	@GetMapping("/getSharedTrip")
	public List<SharingDto> getSharedTrip(@RequestHeader(value = "X_USER_PAYLOAD", required = false) String userId) {
		return sharingService.getSharedTrip(userId);
	}
}
