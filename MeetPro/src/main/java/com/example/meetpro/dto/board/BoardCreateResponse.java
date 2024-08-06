package com.example.meetpro.dto.board;

import com.example.meetpro.entity.board.Board;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NotBlank
public class BoardCreateResponse {

    private Long id;
    private String title;
    private String content;

    public static BoardCreateResponse toDto(Board board) {
        return new BoardCreateResponse(board.getId(), board.getTitle(), board.getContent());
    }
}
