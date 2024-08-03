package com.example.meetpro.dto.member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberCntDto {

    private Long totalMemberCnt;
    private Long totalSeniorCnt;
    private Long totalAdminCnt;
}
