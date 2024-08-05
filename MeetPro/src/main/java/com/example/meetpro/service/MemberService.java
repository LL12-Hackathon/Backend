package com.example.meetpro.service;

import com.example.meetpro.IResult;
import com.example.meetpro.domain.Member;
import com.example.meetpro.dto.JoinRequest;
import com.example.meetpro.dto.LoginRequest;
import com.example.meetpro.entities.UserEntity;
import com.example.meetpro.enums.CommonResult;
import com.example.meetpro.mappers.IMemberMapper;
import com.example.meetpro.repository.MemberRepository;
import com.example.meetpro.utils.CryptoUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
//@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final IMemberMapper memberMapper;

//    @Autowired
    @Autowired
    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder, IMemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.memberMapper = memberMapper;
    }

    public boolean checkLoginIdDuplicate(String loginId){
        return memberRepository.existsByLoginId(loginId);
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

    public Member getLoginMemberByLoginId(String loginId){
        if(loginId == null) return null;
        return memberRepository.findByLoginId(loginId);
    }

    public Enum<? extends IResult> register(UserEntity user) {
      user.setPassword(CryptoUtils.hashSah512(user.getPassword()));
        int result = memberMapper.insertUser(user);
        // 결과에 따른 Enum 값 반환
        return result > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }
}