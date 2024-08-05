package com.example.meetpro.service;

import com.example.meetpro.domain.Member;
import com.example.meetpro.domain.MemberRole;
import com.example.meetpro.dto.JoinRequest;
import com.example.meetpro.dto.LoginRequest;
import com.example.meetpro.dto.member.MemberCntDto;
import com.example.meetpro.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean checkLoginIdDuplicate(String loginId){
        return memberRepository.existsByLoginId(loginId);
    }

    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    public void join(JoinRequest joinRequest) {
        memberRepository.save(joinRequest.toEntity());
    }

    public void securityJoin(JoinRequest joinRequest){
        if(memberRepository.existsByLoginId(joinRequest.getLoginId())){
            return;
        }

        joinRequest.setPassword(bCryptPasswordEncoder.encode(joinRequest.getPassword()));

        memberRepository.save(joinRequest.toEntity());
    }

    public Member login(LoginRequest loginRequest) {
        Member findMember = memberRepository.findByLoginId(loginRequest.getLoginId());

        if(findMember == null){
            return null;
        }

        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), findMember.getPassword())) {
            return null;
        }

        return findMember;
    }



    public Member getLoginMemberById(Long memberId){
        if(memberId == null) return null;

        Optional<Member> findMember = memberRepository.findById(memberId);
        return findMember.orElse(null);

    }

    public Member getLoginMemberByLoginId(String login_id){
        if(login_id == null) return null;

        return memberRepository.findByLoginId(login_id);

    }
    public MemberCntDto getUserCnt() {
        return MemberCntDto.builder()
                .totalMemberCnt(memberRepository.count())
                .totalSeniorCnt(memberRepository.countByRole(MemberRole.ADMIN))
                .totalAdminCnt(memberRepository.countByRole(MemberRole.ADMIN))
                .build();
    }

}