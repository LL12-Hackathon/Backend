package com.example.meetpro.repository.board;

import com.example.meetpro.domain.board.Board;
import com.example.meetpro.domain.board.BoardCategory;
import com.example.meetpro.domain.MemberRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByCategoryAndTitleContainsAndMemberDetailsMemberRoleNot(BoardCategory category, String keyword, MemberRole role, PageRequest pageRequest);
    Page<Board> findAllByCategoryAndMemberDetailsMemberNicknameContainsAndMemberDetailsMemberRoleNot(BoardCategory category, String keyword, MemberRole role, PageRequest pageRequest);
    Page<Board> findAllByCategoryAndMemberDetailsMemberRoleNot(BoardCategory category, MemberRole role, PageRequest pageRequest);
    List<Board> findAllByCategoryAndMemberDetailsMemberRole(BoardCategory category, MemberRole role);
}