package com.example.meetpro.dto.board;

import com.example.meetpro.domain.board.Board;
import com.example.meetpro.domain.board.UploadImage;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardDto {

    private Long id;
    private String memberLoginId;
    private String memberNickname;
    private String title;
    private String body;
    private Integer likeCnt;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private MultipartFile newImage;
    private UploadImage uploadImage;

    public static BoardDto of(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .memberLoginId(board.getMember().getLoginId())
                .memberNickname(board.getMember().getNickname())
                .title(board.getTitle())
                .body(board.getBody())
                .createdAt(board.getCreatedAt())
                .lastModifiedAt(board.getLastModifiedAt())
                .likeCnt(board.getLikes().size())
                .uploadImage(board.getUploadImage())
                .build();
    }
}