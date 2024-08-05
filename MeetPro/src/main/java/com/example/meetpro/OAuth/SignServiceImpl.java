package com.example.meetpro.OAuth;

import com.example.meetpro.OAuth.kakao.KakaoUser;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;

public class SignServiceImpl {
    public String createKakaoToken(String code) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://kauth.kakao.com/oauth/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=" + "authorization_code" +
                                "&client_id=" + kakaoClientId +
                                "&redirect_uri=" + kakaoRedirectUri +
                                "&code=" + code
                ))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(response.body());

        return jsonObject.getString("access_token");
    }

    public KakaoUser getKakaoInfo(String accessToken) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://kapi.kakao.com/v2/user/me"))
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .header("Authorization", "Bearer " + accessToken)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(response.body());

        JSONObject kakao_account = jsonObject.getJSONObject("kakao_account");
        JSONObject profile = kakao_account.getJSONObject("profile");

        KakaoUser kakaoUser = new KakaoUser();
        kakaoUser.setEmail(kakao_account.getString("email"));
        kakaoUser.setNickname(profile.getString("nickname"));
        kakaoUser.setProfileImageUrl(profile.getString("profile_image_url"));

        return kakaoUser;
    }
    public String getRandomPwd() {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";

        String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }

    @Override
    public SignInResultDto kakaoLogin(String code) throws RuntimeException, IOException, InterruptedException {
        String kakaoUserToken = createKakaoToken(code);
        KakaoUser kakaoUser = getKakaoInfo(kakaoUserToken);

//        카카오톡 로그인 시, 이미 가입된 회원이라면
        if(userRepository.getByUid(kakaoUser.getEmail()) != null) {
            User user = userRepository.getByUid(kakaoUser.getEmail());

            user.setRecentLoggedIn(LocalDateTime.now());
            userRepository.save(user);

            SignInResultDto signInResultDto = SignInResultDto.builder()
                    .token(jwtTokenProvider.createAccessToken(String.valueOf(user.getUid()), user.getRoles()))
                    .refreshToken(jwtTokenProvider.createRefreshToken(String.valueOf(user.getUid())))
                    .uid(user.getUid())
                    .name(user.getName())
                    .build();

            setSuccessResult(signInResultDto);

            return signInResultDto;
        } else {
//            카카오 프로필 이미지를 가져올 수 없을 때, 서비스 기본 프로필 사용
            if(kakaoUser.getProfileImageUrl() == null) {
                kakaoUser.setProfileImageUrl(DEFAULT_PROFILE);
            }

            if(kakaoUser.getEmail() == null || kakaoUser.getNickname() == null) {
                throw new RuntimeException("카카오 정보에서 이메일 또는 닉네임을 가져올 수 없습니다");
            }

            User user = User.builder()
                    .uid(kakaoUser.getEmail())
                    .name(kakaoUser.getNickname())
                    .createdAt(LocalDateTime.now())
                    .useAble(true)
                    .registerType(RegisterType.KAKAO)
                    .profileUrl(kakaoUser.getProfileImageUrl())
                    .password(passwordEncoder.encode(getRandomPassword()))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();

            if(!validateUid(user.getUid())) {
                throw new RuntimeException("이메일 주소 형식이 아닙니다.");
            } else if(userRepository.getByUid(kakaoUser.getEmail()) != null) {
                throw new RuntimeException("이미 존재하는 회원입니다.");
            } else {
                userRepository.save(user);
            }

//            회원가입 후 로그인 정보 반환
            SignInResultDto signInResultDto = SignInResultDto.builder()
                    .token(jwtTokenProvider.createAccessToken(String.valueOf(user.getUid()), user.getRoles()))
                    .refreshToken(jwtTokenProvider.createRefreshToken(String.valueOf(user.getUid())))
                    .uid(user.getUid())
                    .name(user.getName())
                    .build();

            setSuccessResult(signInResultDto);

            return signInResultDto;
        }
    }
}
