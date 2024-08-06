package com.example.meetpro.repository.board;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.meetpro.entity.board.Board;
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByTitleContaining(String keyword, Pageable pageable);

    Page<Board> findAll(Pageable pageable);

    Page<Board> findAllByCategoryId(Pageable pageable, int categoryId);

    Page<Board> findByLikedGreaterThanEqual(Pageable pageable, int number);

    List<Board> findByReportedIsTrue();
}
