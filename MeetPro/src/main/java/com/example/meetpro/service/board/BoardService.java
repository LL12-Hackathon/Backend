package com.example.meetpro.service.board;

import com.example.meetpro.entity.category.Category;
import com.example.meetpro.entity.user.User;
import com.example.meetpro.repository.board.BoardRepository;
import com.example.meetpro.repository.board.LikeBoardRepository;
import com.example.meetpro.repository.category.CategoryRepository;
import com.example.meetpro.service.file.FileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.meetpro.dto.board.BoardCreateRequest;
import com.example.meetpro.dto.board.BoardCreateResponse;
import com.example.meetpro.dto.board.BoardFindAllWithPagingResponseDto;
import com.example.meetpro.dto.board.BoardResponseDto;
import com.example.meetpro.dto.board.BoardSimpleDto;
import com.example.meetpro.dto.board.BoardUpdateRequest;
import com.example.meetpro.dto.board.PageInfoDto;
import com.example.meetpro.entity.board.Board;
import com.example.meetpro.entity.board.Image;
import com.example.meetpro.entity.board.LikeBoard;

import com.example.meetpro.exception.CategoryNotFoundException;
import com.example.meetpro.exception.LikeHistoryNotfoundException;
import com.example.meetpro.exception.MemberNotEqualsException;
import com.example.meetpro.exception.BoardNotFoundException;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
public class BoardService {

    private static final String SUCCESS_LIKE_BOARD = "좋아요 처리 완료";
    private static final String SUCCESS_UNLIKE_BOARD = "좋아요 취소 완료";
    private static final int RECOMMEND_SET_COUNT = 10;
    private static final int PAGE_SIZE = 10;
    private static final String SORTED_BY_ID = "id";

    private final BoardRepository boardRepository;
    private final FileService fileService;
    private final LikeBoardRepository likeBoardRepository;
    private final CategoryRepository categoryRepository;

    public BoardService(final BoardRepository boardRepository, final FileService fileService, final LikeBoardRepository likeBoardRepository,  final CategoryRepository categoryRepository) {
        this.boardRepository = boardRepository;
        this.fileService = fileService;
        this.likeBoardRepository = likeBoardRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public BoardCreateResponse createBoard(final BoardCreateRequest req, final int categoryId, final User user) {
        List<Image> images = req.getImages().stream()
                .map(image -> Image.from(image.getOriginalFilename()))
                .collect(toList());

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);

        Board board = boardRepository.save(new Board(req.getTitle(), req.getContent(), user, category, images));

        uploadImages(board.getImages(), req.getImages());
        return BoardCreateResponse.toDto(board);
    }

    @Transactional(readOnly = true)
    public BoardFindAllWithPagingResponseDto findAllBoards(final Integer page, final int categoryId) {
        Page<Board> boards = makePageBoards(page, categoryId);
        return responsePagingBoards(boards);
    }

    private BoardFindAllWithPagingResponseDto responsePagingBoards(final Page<Board> boards) {
        List<BoardSimpleDto> boardSimpleDtoList = boards.stream()
                .map(BoardSimpleDto::toDto)
                .collect(toList());

        return BoardFindAllWithPagingResponseDto.toDto(boardSimpleDtoList, new PageInfoDto(boards));
    }

    private Page<Board> makePageBoards(final Integer page, final int categoryId) {
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, Sort.by(SORTED_BY_ID).descending());
        return boardRepository.findAllByCategoryId(pageRequest, categoryId);
    }

    @Transactional(readOnly = true)
    public BoardResponseDto findBoard(final Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(BoardNotFoundException::new);

        User user = board.getUser();
        return BoardResponseDto.toDto(board, user.getNickname());
    }

    @Transactional
    public String updateLikeOfBoard(final Long id, final User user) {
        Board board = boardRepository.findById(id)
                .orElseThrow(BoardNotFoundException::new);

        if (!hasLikeBoard(board, user)) {
            board.increaseLikeCount();
            return createLikeBoard(board, user);
        }

        board.decreaseLikeCount();
        return removeLikeBoard(board, user);
    }

    private String removeLikeBoard(final Board board, final User user) {
        LikeBoard likeBoard = likeBoardRepository.findByBoardAndUser(board, user)
                .orElseThrow(LikeHistoryNotfoundException::new);

        likeBoardRepository.delete(likeBoard);

        return SUCCESS_UNLIKE_BOARD;
    }


    @Transactional(readOnly = true)
    public List<BoardSimpleDto> findBestBoards(final Pageable pageable) {
        Page<Board> boards = boardRepository.findByLikedGreaterThanEqual(pageable, RECOMMEND_SET_COUNT);

        return boards.stream()
                .map(BoardSimpleDto::toDto)
                .collect(toList());
    }

    @Transactional
    public BoardResponseDto editBoard(final Long id, final BoardUpdateRequest req, final User user) {
        Board board = boardRepository.findById(id)
                .orElseThrow(BoardNotFoundException::new);

        validateBoardOwner(user, board);

        Board.ImageUpdatedResult result = board.update(req);

        uploadImages(result.getAddedImages(), result.getAddedImageFiles());
        deleteImages(result.getDeletedImages());

        return BoardResponseDto.toDto(board, user.getNickname());
    }

    @Transactional
    public void deleteBoard(final Long boardId, final User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        validateBoardOwner(user, board);
        boardRepository.delete(board);
    }

    @Transactional(readOnly = true)
    public List<BoardSimpleDto> searchBoard(final String keyword, final Pageable pageable) {
        Page<Board> boards = boardRepository.findByTitleContaining(keyword, pageable);

        return boards.stream()
                .map(BoardSimpleDto::toDto)
                .collect(toList());
    }

    private void uploadImages(final List<Image> uploadedImages, final List<MultipartFile> fileImages) {
        IntStream.range(0, uploadedImages.size())
                .forEach(uploadedImage -> fileService.upload(
                        fileImages.get(uploadedImage),
                        uploadedImages.get(uploadedImage).getUniqueName())
                );
    }

    private void deleteImages(final List<Image> deletedImages) {
        deletedImages.forEach(deletedImage -> fileService.delete(deletedImage.getUniqueName()));
    }

    public void validateBoardOwner(final User user, final Board board) {
        if (!user.equals(board.getUser())) {
            throw new MemberNotEqualsException();
        }
    }

    public String createLikeBoard(final Board board, final User user) {
        LikeBoard likeBoard = new LikeBoard(board, user);
        likeBoardRepository.save(likeBoard);
        return SUCCESS_LIKE_BOARD;
    }

    public boolean hasLikeBoard(final Board board, final User user) {
        return likeBoardRepository.findByBoardAndUser(board, user)
                .isPresent();
    }
}

