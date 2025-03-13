package com.asd.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private String id;
	private String password;
	private String nickname;
	private String email;
	
	public static UserDto toDto(String id, String password, String nickname, String email) {
        return new UserDto(
        		id,
        		password,
        		nickname,
        		email
        );
    }
}
