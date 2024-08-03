package com.example.meetpro.service.board;

import com.example.meetpro.domain.Member;
import com.example.meetpro.domain.board.Board;
import com.example.meetpro.domain.board.Like;
import com.example.meetpro.repository.MemberRepository;
import com.example.meetpro.repository.board.BoardRepository;
import com.example.meetpro.repository.board.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void addLike(String loginId, Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        Member loginUser = memberRepository.findByLoginId(loginId);
        Member boardUser = board.getMember();

        // 자신이 누른 좋아요가 아니라면
        if (!boardUser.equals(loginUser)) {
            boardUser.likeChange(boardUser.getReceivedLikeCnt() + 1);
        }
        board.likeChange(board.getLikeCnt() + 1);

        likeRepository.save(Like.builder()
                .member(loginUser)
                .board(board)
                .build());
    }

    @Transactional
    public void deleteLike(String loginId, Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        Member loginUser = memberRepository.findByLoginId(loginId);
        Member boardUser = board.getMember();

        // 자신이 누른 좋아요가 아니라면
        if (!boardUser.equals(loginUser)) {
            boardUser.likeChange(boardUser.getReceivedLikeCnt() - 1);
        }
        board.likeChange(board.getLikeCnt() - 1);

        likeRepository.deleteByMemberLoginIdAndBoardId(loginId, boardId);
    }

    public Boolean checkLike(String loginId, Long boardId) {
        return likeRepository.existsByMemberLoginIdAndBoardId(loginId, boardId);
    }
}