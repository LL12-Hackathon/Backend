package com.example.meetpro.dto.board;

import com.example.meetpro.domain.Member;
import com.example.meetpro.domain.board.Board;
import com.example.meetpro.domain.board.BoardCategory;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoardCreateRequest {

    private String title;
    private String body;
    private MultipartFile uploadImage;

    public Board toEntity(BoardCategory category, Member member) {
        return Board.builder()
                .member(member)
                .category(category)
                .title(title)
                .body(body)
                .likeCnt(0)
                .commentCnt(0)
                .build();
    }
}
