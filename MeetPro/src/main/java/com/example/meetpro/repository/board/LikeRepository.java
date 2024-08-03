package com.example.meetpro.repository.board;

import com.example.meetpro.domain.board.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    void deleteByMemberLoginIdAndBoardId(String loginId, Long boardId);
    Boolean existsByMemberLoginIdAndBoardId(String loginId, Long boardId);
    List<Like> findAllByMemberLoginId(String loginId);
}