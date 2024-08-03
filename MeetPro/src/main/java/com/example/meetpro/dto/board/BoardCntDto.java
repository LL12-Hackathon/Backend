package com.example.meetpro.dto.board;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardCntDto {

    private Long totalNoticeCnt;
    private Long totalBoardCnt;
    private Long totalFreeCnt;
    private Long totalCounselingCnt;
    private Long totalJobCnt;
    private Long totalQnACnt;

}