package com.example.meetpro.OAuth.authentication.domain.oauth;

import com.example.meetpro.OAuth.member.OAuthProvider;

public interface OAuthInfoResponse {

    String getProviderId();
    String getEmail();
    String getNickname();
    OAuthProvider getOAuthProvider();

}
