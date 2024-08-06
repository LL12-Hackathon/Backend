package com.example.meetpro;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST"), USER("ROLE_USER"),ADMIN("ROLE_ADMIN"),;

    private final String key;
}