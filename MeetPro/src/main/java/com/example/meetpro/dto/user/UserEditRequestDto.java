package com.example.meetpro.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.meetpro.entity.user.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEditRequestDto {

    private String name;
    private String nickname;

    public static UserEditRequestDto toDto(User user){
        return new UserEditRequestDto(user.getName(), user.getNickname());
    }
}
