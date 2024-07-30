package com.example.meetpro.google;

import com.example.meetpro.OAuth.authentication.domain.oauth.OAuthInfoResponse;
import com.example.meetpro.OAuth.member.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleInfoResponse implements OAuthInfoResponse {

    @JsonProperty("sub")
    private String providerId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String nickname;

    @Override
    public String getProviderId() {
        return providerId;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.GOOGLE;
    }
}