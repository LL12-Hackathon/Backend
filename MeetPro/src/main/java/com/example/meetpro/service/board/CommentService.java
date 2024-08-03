package com.example.meetpro.service.board;

import com.example.meetpro.domain.Member;
import com.example.meetpro.domain.MemberRole;
import com.example.meetpro.domain.board.Board;
import com.example.meetpro.domain.board.Comment;
import com.example.meetpro.dto.board.CommentCreateRequest;
import com.example.meetpro.repository.MemberRepository;
import com.example.meetpro.repository.board.BoardRepository;
import com.example.meetpro.repository.board.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void writeComment(Long boardId, CommentCreateRequest req, String loginId) {
        Board board = boardRepository.findById(boardId).get();
        Member member = memberRepository.findByLoginId(loginId);
        board.commentChange(board.getCommentCnt() + 1);
        commentRepository.save(req.toEntity(board, member));
    }

    public List<Comment> findAll(Long boardId) {
        return commentRepository.findAllByBoardId(boardId);
    }

    @Transactional
    public Long editComment(Long commentId, String newBody, String loginId) {
        Optional<Comment> optComment = commentRepository.findById(commentId);
        Member optUser = memberRepository.findByLoginId(loginId);
        if (optComment.isEmpty() || optUser == null || !optComment.get().getMember().equals(optUser)) {
            return null;
        }

        Comment comment = optComment.get();
        comment.update(newBody);

        return comment.getBoard().getId();
    }

    public Long deleteComment(Long commentId, String loginId) {
        Optional<Comment> optComment = commentRepository.findById(commentId);
        Member optUser = memberRepository.findByLoginId(loginId);
        if (optComment.isEmpty() || optUser == null ||
                (!optComment.get().getMember().equals(optUser) && !optUser.getRole().equals(MemberRole.ADMIN))) {
            return null;
        }

        Board board = optComment.get().getBoard();
        board.commentChange(board.getCommentCnt() - 1);

        commentRepository.delete(optComment.get());
        return board.getId();
    }
}