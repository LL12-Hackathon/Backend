package com.example.meetpro.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.meetpro.entity.user.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleNicknameResponseDto {
    private String name;
    private String nickname;

    public static UserSimpleNicknameResponseDto toDto(User user) {
        return new UserSimpleNicknameResponseDto(user.getName(), user.getNickname());
    }
}
