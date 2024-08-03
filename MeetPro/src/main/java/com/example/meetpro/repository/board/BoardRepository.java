package com.example.meetpro.repository.board;

import com.example.meetpro.domain.MemberRole;
import com.example.meetpro.domain.board.Board;
import com.example.meetpro.domain.board.BoardCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAllByCategoryAndMemberRoleNot(BoardCategory category, MemberRole Role, PageRequest pageRequest);
    Page<Board> findAllByCategoryAndTitleContainsAndMemberRoleNot(BoardCategory category, String title, MemberRole Role, PageRequest pageRequest);
    Page<Board> findAllByCategoryAndMemberNicknameContainsAndMemberRoleNot(BoardCategory category, String nickname, MemberRole Role, PageRequest pageRequest);
    List<Board> findAllByMemberLoginId(String loginId);
    List<Board> findAllByCategoryAndMemberRole(BoardCategory category, MemberRole Role);
    Long countAllByMemberRole(MemberRole Role);
    Long countAllByCategoryAndMemberRoleNot(BoardCategory category, MemberRole Role);
}
