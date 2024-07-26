package com.example.meetpro.OAuth.authentication.infra.naver;

import com.example.meetpro.OAuth.authentication.domain.oauth.OAuthLoginParams;
import com.example.meetpro.OAuth.member.OAuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@NoArgsConstructor
public class NaverLoginParams implements OAuthLoginParams {
    private String authorizationCode;
    private String state;

    @Override
    public OAuthProvider oAuthProvider(){
        return OAuthProvider.NAVER;
    }

    @Override
    public MultiValueMap<String, String> makeBody(){
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        body.add("state", state);
        return body;
    }
}
