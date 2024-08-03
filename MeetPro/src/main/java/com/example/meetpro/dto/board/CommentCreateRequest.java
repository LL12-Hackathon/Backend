package com.example.meetpro.dto.board;
import com.example.meetpro.domain.Member;
import com.example.meetpro.domain.board.Board;
import com.example.meetpro.domain.board.Comment;
import lombok.Data;

@Data
public class CommentCreateRequest {

    private String body;

    public Comment toEntity(Board board, Member member) {
        return Comment.builder()
                .member(member)
                .board(board)
                .body(body)
                .build();
    }
}