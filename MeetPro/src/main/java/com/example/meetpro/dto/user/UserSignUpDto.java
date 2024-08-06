package com.example.meetpro.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter

public class UserSignUpDto {

    private String name;
    private String email;
    private String password;
    private String nickname;
}
