package com.example.meetpro.dto.member;

import com.example.meetpro.domain.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDto {

    private String loginId;
    private String nickname;
    private String nowPassword;
    private String newPassword;
    private String newPasswordCheck;

    public static MemberDto of(Member member){
        return MemberDto.builder()
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .build();
    }
}
