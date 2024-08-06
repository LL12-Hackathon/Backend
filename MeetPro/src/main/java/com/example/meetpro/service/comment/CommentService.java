package com.example.meetpro.service.comment;


import com.example.meetpro.dto.comment.CommentCreateRequest;
import com.example.meetpro.dto.comment.CommentDto;
import com.example.meetpro.dto.comment.CommentReadCondition;
import com.example.meetpro.entity.board.Board;
import com.example.meetpro.entity.comment.Comment;
import com.example.meetpro.exception.MemberNotEqualsException;
import com.example.meetpro.repository.board.BoardRepository;
import com.example.meetpro.repository.comment.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import com.example.meetpro.entity.user.User;
import com.example.meetpro.exception.BoardNotFoundException;
import com.example.meetpro.exception.CommentNotFoundException;


@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public CommentService(final CommentRepository commentRepository, final BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional(readOnly = true)
    public List<CommentDto> findAllComments(final CommentReadCondition condition) {
        return commentRepository.findByBoardId(condition.getBoardId()).stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto createComment(final CommentCreateRequest req, final User user) {
        Board board = boardRepository.findById(req.getBoardId())
                .orElseThrow(BoardNotFoundException::new);

        Comment comment = new Comment(req.getContent(), user, board);
        commentRepository.save(comment);

        return CommentDto.toDto(comment);
    }

    @Transactional
    public void deleteComment(final Long id, final User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        validateDeleteComment(comment, user);
        commentRepository.delete(comment);
    }

    private void validateDeleteComment(final Comment comment, final User user) {
        if (!comment.isOwnComment(user)) {
            throw new MemberNotEqualsException();
        }
    }
}
