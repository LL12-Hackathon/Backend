package com.example.meetpro.service.board;

import com.example.meetpro.domain.Member;
import com.example.meetpro.domain.MemberRole;
import com.example.meetpro.domain.board.*;
import com.example.meetpro.dto.board.BoardCntDto;
import com.example.meetpro.dto.board.BoardCreateRequest;
import com.example.meetpro.dto.board.BoardDto;
import com.example.meetpro.repository.MemberRepository;
import com.example.meetpro.repository.board.BoardRepository;
import com.example.meetpro.repository.board.CommentRepository;
import com.example.meetpro.repository.board.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
//    private final S3UploadService s3UploadService;
     private final UploadImageService uploadImageService; //=> 로컬 디렉토리에 저장할 때 사용 => S3UploadService 대신 사용

    public Page<Board> getBoardList(BoardCategory category, PageRequest pageRequest, String searchType, String keyword) {
        if (searchType != null && keyword != null) {
            if (searchType.equals("title")) {
                return boardRepository.findAllByCategoryAndTitleContainsAndMemberRoleNot(category, keyword, MemberRole.ADMIN, pageRequest);
            } else {
                return boardRepository.findAllByCategoryAndMemberNicknameContainsAndMemberRoleNot(category, keyword, MemberRole.ADMIN, pageRequest);
            }
        }
        return boardRepository.findAllByCategoryAndMemberRoleNot(category, MemberRole.ADMIN, pageRequest);
    }

    public List<Board> getNotice(BoardCategory category) {
        return boardRepository.findAllByCategoryAndMemberRole(category, MemberRole.ADMIN);
    }

    public BoardDto getBoard(Long boardId, String category) {
        Optional<Board> optBoard = boardRepository.findById(boardId);

        // id에 해당하는 게시글이 없거나 카테고리가 일치하지 않으면 null return
        if (optBoard.isEmpty() || !optBoard.get().getCategory().toString().equalsIgnoreCase(category)) {
            return null;
        }

        return BoardDto.of(optBoard.get());
    }

    @Transactional
    public Long writeBoard(BoardCreateRequest req, BoardCategory category, String loginId, Authentication auth) throws IOException {
        Member loginUser = memberRepository.findByLoginId(loginId);

        Board savedBoard = boardRepository.save(req.toEntity(category, loginUser));

        UploadImage uploadImage = uploadImageService.saveImage(req.getUploadImage(), savedBoard);
        if (uploadImage != null) {
            savedBoard.setUploadImage(uploadImage);
        }
        return savedBoard.getId();
    }

    @Transactional
    public Long editBoard(Long boardId, String category, BoardDto dto) throws IOException {
        Optional<Board> optBoard = boardRepository.findById(boardId);

        // id에 해당하는 게시글이 없거나 카테고리가 일치하지 않으면 null return
        if (optBoard.isEmpty() || !optBoard.get().getCategory().toString().equalsIgnoreCase(category)) {
            return null;
        }

        Board board = optBoard.get();
        // 게시글에 이미지가 있었으면 삭제
        if (board.getUploadImage() != null) {
            uploadImageService.deleteImage(board.getUploadImage());
            board.setUploadImage(null);
        }

        UploadImage uploadImage = uploadImageService.saveImage(dto.getNewImage(), board);
        if (uploadImage != null) {
            board.setUploadImage(uploadImage);
        }
        board.update(dto);

        return board.getId();
    }

    @Transactional
    public Long deleteBoard(Long boardId, String category) throws IOException {
        Optional<Board> optBoard = boardRepository.findById(boardId);

        // id에 해당하는 게시글이 없거나 카테고리가 일치하지 않으면 null return
        if (optBoard.isEmpty() || !optBoard.get().getCategory().toString().equalsIgnoreCase(category)) {
            return null;
        }

        Board board = optBoard.get();
        Member boardUser = board.getMember();
        boardUser.likeChange(boardUser.getReceivedLikeCnt() - board.getLikeCnt());
        if (board.getUploadImage() != null) {
            uploadImageService.deleteImage(board.getUploadImage());
            board.setUploadImage(null);
        }
        boardRepository.deleteById(boardId);
        return boardId;
    }

    public String getCategory(Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        return board.getCategory().toString().toLowerCase();
    }

    public List<Board> findMyBoard(String category, String loginId) {
        if (category.equals("board")) {
            return boardRepository.findAllByMemberLoginId(loginId);
        } else if (category.equals("like")) {
            List<Like> likes = likeRepository.findAllByMemberLoginId(loginId);
            List<Board> boards = new ArrayList<>();
            for (Like like : likes) {
                boards.add(like.getBoard());
            }
            return boards;
        } else if (category.equals("comment")) {
            List<Comment> comments = commentRepository.findAllByMemberLoginId(loginId);
            List<Board> boards = new ArrayList<>();
            HashSet<Long> commentIds = new HashSet<>();

            for (Comment comment : comments) {
                if (!commentIds.contains(comment.getBoard().getId())) {
                    boards.add(comment.getBoard());
                    commentIds.add(comment.getBoard().getId());
                }
            }
            return boards;
        }
        return null;
    }

    public BoardCntDto getBoardCnt(){
        return BoardCntDto.builder()
                .totalBoardCnt(boardRepository.count())
                .totalNoticeCnt(boardRepository.countAllByMemberRole(MemberRole.ADMIN))
                .totalCounselingCnt(boardRepository.countAllByCategoryAndMemberRoleNot(BoardCategory.COUNSELING, MemberRole.ADMIN))
                .totalFreeCnt(boardRepository.countAllByCategoryAndMemberRoleNot(BoardCategory.FREE, MemberRole.ADMIN))
                .totalQnACnt(boardRepository.countAllByCategoryAndMemberRoleNot(BoardCategory.FREE, MemberRole.ADMIN))
                .build();
    }
}