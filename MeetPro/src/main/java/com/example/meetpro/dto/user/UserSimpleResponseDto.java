package com.example.meetpro.dto.user;

import com.example.meetpro.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSimpleResponseDto {

    private String Social_id;
    private String name;

    public static UserSimpleResponseDto toDto(User user) {
        return new UserSimpleResponseDto(user.getSocialId(), user.getName());
    }
}