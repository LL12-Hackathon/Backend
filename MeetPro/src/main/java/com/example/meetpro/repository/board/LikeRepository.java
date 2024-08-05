// LikeRepository.java
package com.example.meetpro.repository.board;

import com.example.meetpro.domain.MemberDetails;
import com.example.meetpro.domain.board.Board;
import com.example.meetpro.domain.board.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    // Ensure this method exists
    Like findByBoardAndMemberDetails(Board board, MemberDetails memberDetails);
}