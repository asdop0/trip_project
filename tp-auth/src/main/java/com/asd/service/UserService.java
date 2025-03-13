package com.asd.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.asd.DTO.UserDto;
import com.asd.repository.RedisRepository;
import com.asd.security.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisRepository RedisRepository;
	private final PasswordEncoder passwordEncoder;
	/*
	 * //전달받은 헤더에서 토큰을 통해 유저 정보 추출 public UserDto getUserInfo(HttpServletRequest
	 * request) { String token = jwtTokenProvider.resolveToken(request); User user =
	 * userRepository.getByUid(jwtTokenProvider.getUsername(token)).orElseThrow(()
	 * -> new IllegalArgumentException("[getUserInfo] 해당 유저를 찾을 수 없습니다.") ); return
	 * UserDto.toDto(user); }
	 * 
	 * //전달받은 유저의 수정 정보로 저장 public void modifyUserInfo(HttpServletRequest request,
	 * User user) { String token = jwtTokenProvider.resolveToken(request); User
	 * user_ =
	 * userRepository.getByUid(jwtTokenProvider.getUsername(token)).orElseThrow(()
	 * -> new IllegalArgumentException("[modifyUserInfo] 해당 유저를 찾을 수 없습니다.") );
	 * user_.setNickname(user.getNickname());
	 * 
	 * userRepository.save(user_); }
	 * 
	 * //전달받은 전화번호로 아이디 찾기 public String findById(String nickname, String name) {
	 * User user = userRepository.getByNicknameAndName(nickname,
	 * name).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.") );
	 * return user.getUid(); }
	 * 
	 * //비밀번호 변경 public boolean modifyPassword(HttpServletRequest request, String
	 * password, String newPassword) { String token =
	 * jwtTokenProvider.resolveToken(request); User user =
	 * userRepository.getByUid(jwtTokenProvider.getUsername(token)).orElseThrow(()
	 * -> new IllegalArgumentException("[modifyPassword] 해당 유저를 찾을 수 없습니다.") );
	 * if(!passwordEncoder.matches(password, user.getPassword())) { return false; }
	 * user.setPassword(passwordEncoder.encode(newPassword));
	 * userRepository.save(user); return true; }
	 */
}
