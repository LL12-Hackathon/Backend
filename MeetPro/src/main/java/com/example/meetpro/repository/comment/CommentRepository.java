package com.example.meetpro.repository.comment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.meetpro.entity.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBoardId(Long boardId);
}
