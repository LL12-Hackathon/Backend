package com.example.meetpro.OAuth.authentication.domain.oauth;

import com.example.meetpro.OAuth.member.OAuthProvider;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
