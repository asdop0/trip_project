package com.asd.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User{
	
	String id;
	
	String password;
	
	String nickname;
	
	String email;
	
	String refreshToken;
	
	String tripCount;
}