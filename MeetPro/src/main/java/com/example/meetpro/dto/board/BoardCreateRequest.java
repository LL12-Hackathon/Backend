package com.example.meetpro.dto.board;

import com.example.meetpro.domain.board.Board;
import com.example.meetpro.domain.board.BoardCategory;
import com.example.meetpro.domain.MemberDetails;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class BoardCreateRequest {
    private String title;
    private String body;
    private MultipartFile uploadImage;

    public Board toEntity(BoardCategory category, MemberDetails memberDetails) {
        return Board.builder()
                .title(title)
                .body(body)
                .category(category)
                .memberDetails(memberDetails)
                .nickname(memberDetails.getMember().getNickname())
                .createdAt(LocalDateTime.now())
                .build();
    }
}