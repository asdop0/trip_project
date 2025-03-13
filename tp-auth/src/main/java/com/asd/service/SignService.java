package com.asd.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.asd.DTO.SignInResultDto;
import com.asd.DTO.SignUpResultDto;
import com.asd.common.CommonResponse;
import com.asd.model.User;
import com.asd.repository.RedisRepository;
import com.asd.security.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignService {
	private final RedisRepository redisRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    
    //회원가입
    public SignUpResultDto signUp(String id, String password, String nickname, String email) {
        // TODO Auto-generated method stub
    	redisRepository.sginUp(id, passwordEncoder.encode(password), nickname, email);
        SignUpResultDto signUpResultDto = new SignUpResultDto();
        if (redisRepository.isKeyExistsWithPattern(id)) {
            setSuccessResult(signUpResultDto);
        } else {
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }
    
	//로그인 
    public SignInResultDto signIn(String id, String password) throws IllegalArgumentException { // TODO Auto-generated method stub 
    	User user = redisRepository.getUser(id);
    	if(!passwordEncoder.matches(password, user.getPassword())) { 
    		throw new IllegalArgumentException("아이디나 비밀번호가 틀립니다."); 
    	} 
    	SignInResultDto signInResultDto = SignInResultDto.builder()
    			.refreshToken(jwtTokenProvider.createRefreshToken(String.valueOf(id)))
    			.accessToken(jwtTokenProvider.createAccessToken(String.valueOf(id)))
    			.nickname(user.getNickname()).build();
    	setSuccessResult(signInResultDto); 
    	return signInResultDto; 
    }
    
    //성공시
    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
    
    //실패시
    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }
    
	//액세스 토큰 재발급 
    public SignInResultDto refresh(HttpServletRequest request) throws RuntimeException{ 
    	String token = jwtTokenProvider.resolveToken(request); 
    	User user = redisRepository.getUser(jwtTokenProvider.getUserKey(token));
    	
    	if(!user.getRefreshToken().equals(token)) {
    		throw new RuntimeException(); 
    	} 
    	SignInResultDto signInResultDto = SignInResultDto.builder()
    			.accessToken(jwtTokenProvider.createAccessToken(String.valueOf(user.getId()))).build();
    	setSuccessResult(signInResultDto); 
    	return signInResultDto; 
    }

	//로그아웃 
    public void signOut(HttpServletRequest request) throws RuntimeException{ 
    	String token = jwtTokenProvider.resolveToken(request);
    	redisRepository.setRefreshToken(jwtTokenProvider.getUserKey(token), "");
	 }

    //회원탈퇴 
    public void delete(HttpServletRequest request) throws RuntimeException{ 
    	String token = jwtTokenProvider.resolveToken(request); 
    	redisRepository.deleteUser(jwtTokenProvider.getUserKey(token));
    	
	}
    
    //아이디 중복 확인 (중복 O == true, X == false)
    public boolean uidCheck(String uid) {
    	return redisRepository.isKeyExistsWithPattern(uid);
    }
    
    //닉네임 중복 확인 (중복 O == true, X == false)
    public boolean nicknameCheck(String nickname) {
    	Set<String> keys = redisRepository.getNicknameList();
    	return keys.contains(nickname);
    }
}
