package com.asd.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asd.DTO.UserDto;
import com.asd.model.User;
import com.asd.service.SignService;
import com.asd.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final SignService signService;
    
	/*
	 * //유저 정보 조회
	 * 
	 * @GetMapping("/info") public UserDto getUserInfo(HttpServletRequest request) {
	 * UserDto userDto = userService.getUserInfo(request); return userDto; }
	 * 
	 * //닉네임 변경
	 * 
	 * @PostMapping("/modify") public Map<String, String>
	 * modifyUserInfo(HttpServletRequest request, @RequestParam String nickname) {
	 * Map<String, String> response = new HashMap<>();
	 * if(!signService.nicknameCheck(nickname)) { response.put("check", "false");
	 * return response; } User user = new User(); user.setNickname(nickname);
	 * userService.modifyUserInfo(request, user); response.put("check", "true");
	 * return response; }
	 * 
	 * //아이디 찾기
	 * 
	 * @GetMapping("/find") public Map<String, String> findById(@RequestParam String
	 * nickname, @RequestParam String name) throws IllegalArgumentException{ String
	 * id = userService.findById(nickname, name); Map<String, String> response = new
	 * HashMap<>(); response.put("id", id); return response; }
	 * 
	 * //비밀번호 변경
	 * 
	 * @PostMapping("/modify/password") public Map<String, String>
	 * modifyPassword(HttpServletRequest request, @RequestBody Map<String, String>
	 * requestData) { Map<String, String> response = new HashMap<>(); boolean bool =
	 * userService.modifyPassword(request, requestData.get("password"),
	 * requestData.get("newPassword")); response.put("check", String.valueOf(bool));
	 * return response; }
	 */
}
