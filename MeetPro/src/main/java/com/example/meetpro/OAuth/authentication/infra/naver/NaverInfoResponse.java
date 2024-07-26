package com.example.meetpro.OAuth.authentication.infra.naver;

import com.example.meetpro.OAuth.authentication.domain.oauth.OAuthInfoResponse;
import com.example.meetpro.OAuth.member.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfoResponse implements OAuthInfoResponse {

    @JsonProperty("response")
    private Response response;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response{
        private String id;
        private String email;
        private String nickname;
    }

    @Override
    public String getProviderId(){
        return response.id;
    }
    @Override
    public String getEmail(){
        return response.email;
    }

    @Override
    public String getNickname(){
        return response.nickname;
    }

    @Override
    public OAuthProvider getOAuthProvider(){
        return OAuthProvider.NAVER;
    }
}
