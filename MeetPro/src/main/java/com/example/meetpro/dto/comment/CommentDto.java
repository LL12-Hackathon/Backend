package com.example.meetpro.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.meetpro.entity.comment.Comment;
import com.example.meetpro.dto.user.UserSimpleNicknameResponseDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private String content;
    private UserSimpleNicknameResponseDto userSimpleNicknameResponseDto;
    private LocalDateTime createdAt;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                UserSimpleNicknameResponseDto.toDto(comment.getUser()),
                comment.getCreatedAt()
        );
    }
}
