package com.example.meetpro.repository.board;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.meetpro.entity.board.Board;
import com.example.meetpro.entity.board.LikeBoard;
import com.example.meetpro.entity.user.User;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {

    Optional<LikeBoard> findByBoardAndUser(Board board, User user);
}
