package com.example.meetpro.OAuth.authentication.application;

import com.example.meetpro.OAuth.authentication.domain.AuthTokens;
//import com.example.meetpro.OAuth.authentication.infra.google.GoogleLoginParams;
import com.example.meetpro.OAuth.authentication.infra.kakao.KakaoLoginParams;
import com.example.meetpro.OAuth.authentication.infra.naver.NaverLoginParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final OAuthLoginService oAuthLoginService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "/kakao", consumes = "application/json")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params){
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    @PostMapping(value = "/naver", consumes = "application/json")
    public ResponseEntity<AuthTokens> loginNaver(@RequestBody NaverLoginParams params){
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

//    @PostMapping(value = "/google", consumes = "application/json")
//    public ResponseEntity<AuthTokens> loginGoogle(@RequestBody GoogleLoginParams params){
//        return ResponseEntity.ok(oAuthLoginService.login(params));
//    }
}