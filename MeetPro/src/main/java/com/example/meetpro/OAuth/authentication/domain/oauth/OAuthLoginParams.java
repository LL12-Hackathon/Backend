package com.example.meetpro.OAuth.authentication.domain.oauth;

import com.example.meetpro.OAuth.member.OAuthProvider;
import org.springframework.util.MultiValueMap;


public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
