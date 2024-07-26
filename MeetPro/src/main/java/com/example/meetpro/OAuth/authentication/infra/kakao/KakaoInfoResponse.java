package com.example.meetpro.OAuth.authentication.infra.kakao;

import com.example.meetpro.OAuth.authentication.domain.oauth.OAuthInfoResponse;
import com.example.meetpro.OAuth.member.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse implements OAuthInfoResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount{
        private String id;
        private KakaoProfile profile;
        private String email;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoProfile{
        private String nickname;
    }

    @Override
    public String getProviderId(){
        return kakaoAccount.id;
    }
    @Override
    public String getEmail(){
        return kakaoAccount.email;
    }

    @Override
    public String getNickname(){
        return kakaoAccount.profile.nickname;
    }

    @Override
    public OAuthProvider getOAuthProvider(){
        return OAuthProvider.KAKAO;
    }
}
