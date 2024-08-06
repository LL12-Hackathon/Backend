package com.example.meetpro.controller.Board;

import com.example.meetpro.dto.comment.CommentCreateRequest;
import com.example.meetpro.dto.comment.CommentReadCondition;
import com.example.meetpro.service.comment.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.meetpro.global.jwt.util.JwtAuth;
import com.example.meetpro.response.Response;
import com.example.meetpro.entity.user.User;

@Api(value = "Comment Controller", tags = "Comment ")
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "댓글 목록 조회", notes = "댓글을 조회 합니다.")
    @GetMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    public Response findAll(@Valid final CommentReadCondition condition) {
        return Response.success(commentService.findAllComments(condition));
    }

    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성 합니다.")
    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @RequestBody final CommentCreateRequest req, final @JwtAuth User user) {
        return Response.success(commentService.createComment(req, user));
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제 합니다.")
    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@ApiParam(value = "댓글 id", required = true) @PathVariable final Long id, @JwtAuth User user) {
        commentService.deleteComment(id, user);
        return Response.success();
    }
}