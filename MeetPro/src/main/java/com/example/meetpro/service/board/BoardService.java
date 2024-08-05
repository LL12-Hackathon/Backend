package com.example.meetpro.service.board;

import com.example.meetpro.domain.MemberDetails;
import com.example.meetpro.domain.MemberRole;
import com.example.meetpro.domain.board.*;
import com.example.meetpro.dto.board.BoardCntDto;
import com.example.meetpro.dto.board.BoardCreateRequest;
import com.example.meetpro.dto.board.BoardDto;
import com.example.meetpro.repository.MemberDetailsRepository;
import com.example.meetpro.repository.board.BoardRepository;
import com.example.meetpro.repository.board.CommentRepository;
import com.example.meetpro.repository.board.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.dao.DataAccessException;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberDetailsRepository memberDetailsRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final UploadImageService uploadImageService;

    private static final Logger logger = Logger.getLogger(BoardService.class.getName());

    public MemberDetails getMemberDetailsByLoginId(String loginId) {
        return memberDetailsRepository.findByLoginId(loginId);
    }

    public Page<Board> getBoardList(BoardCategory category, PageRequest pageRequest, String searchType, String keyword) {
        try {
            if (searchType != null && keyword != null) {
                if (searchType.equals("title")) {
                    return boardRepository.findAllByCategoryAndTitleContainsAndMemberDetailsMemberRoleNot(category, keyword, MemberRole.ADMIN, pageRequest);
                } else {
                    return boardRepository.findAllByCategoryAndMemberDetailsMemberNicknameContainsAndMemberDetailsMemberRoleNot(category, keyword, MemberRole.ADMIN, pageRequest);
                }
            }
            return boardRepository.findAllByCategoryAndMemberDetailsMemberRoleNot(category, MemberRole.ADMIN, pageRequest);
        } catch (DataAccessException e) {
            logger.severe("Error fetching board list: " + e.getMessage());
            return Page.empty();
        }
    }

    public List<Board> getNotice(BoardCategory category) {
        try {
            return boardRepository.findAllByCategoryAndMemberDetailsMemberRole(category, MemberRole.ADMIN);
        } catch (DataAccessException e) {
            logger.severe("Error fetching notices: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public BoardDto getBoard(Long boardId, String category) {
        try {
            Optional<Board> optBoard = boardRepository.findById(boardId);

            if (optBoard.isEmpty() || !optBoard.get().getCategory().toString().equalsIgnoreCase(category)) {
                return null;
            }

            return BoardDto.of(optBoard.get());
        } catch (DataAccessException e) {
            logger.severe("Error fetching board: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public Long writeBoard(BoardCreateRequest req, BoardCategory category, String loginId, Authentication auth) throws IOException {
        try {
            MemberDetails loginUser = memberDetailsRepository.findByLoginId(loginId);
            logger.info("loginUser: " + loginUser);
            Board savedBoard = boardRepository.save(req.toEntity(category, loginUser));

            if (req.getUploadImage() != null) {
                UploadImage uploadImage = uploadImageService.saveImage(req.getUploadImage(), savedBoard);
                if (uploadImage != null) {
                    savedBoard.setUploadImage(uploadImage);
                }
            }
            return savedBoard.getId();
        } catch (Exception e) {
            logger.severe("Error writing board: " + e.getMessage());
            throw new IOException("Database error occurred while writing board", e);
        }
    }

    @Transactional
    public Long deleteBoard(Long boardId, String category, String loginId) {
        try {
            Optional<Board> optBoard = boardRepository.findById(boardId);

            if (optBoard.isEmpty() || !optBoard.get().getCategory().toString().equalsIgnoreCase(category)) {
                return null;
            }

            MemberDetails memberDetails = memberDetailsRepository.findByLoginId(loginId);
            if (!optBoard.get().getMemberDetails().equals(memberDetails)) {
                return null;
            }

            boardRepository.deleteById(boardId);
            return boardId;
        } catch (DataAccessException e) {
            logger.severe("Error deleting board: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public Long editBoard(Long boardId, String category, BoardDto dto, String loginId) throws IOException {
        try {
            Optional<Board> optBoard = boardRepository.findById(boardId);

            if (optBoard.isEmpty() || !optBoard.get().getCategory().toString().equalsIgnoreCase(category)) {
                return null;
            }

            MemberDetails memberDetails = memberDetailsRepository.findByLoginId(loginId);
            if (!optBoard.get().getMemberDetails().equals(memberDetails)) {
                return null;
            }

            Board board = optBoard.get();
            board.update(dto);

            if (dto.getNewImage() != null) {
                UploadImage uploadImage = uploadImageService.saveImage(dto.getNewImage(), board);
                if (uploadImage != null) {
                    board.setUploadImage(uploadImage);
                }
            }
            return board.getId();
        } catch (DataAccessException e) {
            logger.severe("Error updating board: " + e.getMessage());
            throw new IOException("Database error occurred while updating board", e);
        }
    }

    public BoardCategory getCategory(Long boardId) {
        try {
            Optional<Board> optBoard = boardRepository.findById(boardId);
            return optBoard.map(Board::getCategory).orElse(null);
        } catch (DataAccessException e) {
            logger.severe("Error fetching board category: " + e.getMessage());
            return null;
        }
    }
}