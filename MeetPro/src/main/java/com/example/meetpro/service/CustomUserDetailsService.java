package com.example.meetpro.service;

import com.example.meetpro.OAuth.kakao.KakaoUserDetails;
import com.example.meetpro.domain.Member;
import com.example.meetpro.dto.CustomSecurityUserDetails;
import com.example.meetpro.repository.MemberRepository;
import com.example.meetpro.repository.board.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByLoginId(username);

        if (member != null) {
            return new CustomSecurityUserDetails(member);
        }
        return null;
    }
}
