// LikeService.java
package com.example.meetpro.service.board;

import com.example.meetpro.domain.MemberDetails;
import com.example.meetpro.domain.board.Board;
import com.example.meetpro.domain.board.Like;
import com.example.meetpro.repository.MemberDetailsRepository;
import com.example.meetpro.repository.board.BoardRepository;
import com.example.meetpro.repository.board.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberDetailsRepository memberDetailsRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void addLike(String loginId, Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        MemberDetails loginUser = memberDetailsRepository.findByLoginId(loginId);
        MemberDetails boardUser = board.getMemberDetails();

        // 자신이 누른 좋아요가 아니라면
        if (!boardUser.equals(loginUser)) {
            boardUser.likeChange(boardUser.getReceivedLikeCnt() + 1);
        }
        board.likeChange(board.getLikeCnt() + 1);

        likeRepository.save(Like.builder()
                .memberDetails(loginUser)
                .board(board)
                .build());
    }

    @Transactional
    public void deleteLike(String loginId, Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        MemberDetails loginUser = memberDetailsRepository.findByLoginId(loginId);
        MemberDetails boardUser = board.getMemberDetails();

        // 자신이 누른 좋아요가 아니라면
        if (!boardUser.equals(loginUser)) {
            boardUser.likeChange(boardUser.getReceivedLikeCnt() - 1);
        }
        board.likeChange(board.getLikeCnt() - 1);

        Like like = likeRepository.findByBoardAndMemberDetails(board, loginUser);
        likeRepository.delete(like);
    }

    public boolean checkLike(String username, Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        MemberDetails memberDetails = memberDetailsRepository.findByLoginId(username);
        return likeRepository.findByBoardAndMemberDetails(board, memberDetails) != null;
    }
}