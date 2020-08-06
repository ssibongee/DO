package com.backend.controller;

import com.backend.dto.comment.Comment;
import com.backend.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * 포스트에 댓글 작성
     * @param comment : 댓글 등록
     * @return 성공적으로 댓글이 등록된 경우 201 반환
     */
    @ApiOperation(value = "댓글 작성", notes = "포스트에 댓글 작성")
    @PostMapping("/api/v3/")
    public HttpStatus save(@RequestBody Comment comment) {
        commentService.save(comment);
        return HttpStatus.CREATED;
    }

    /**
     * 포스트의 댓글의 대댓글 작성
     * @param comment
     * @return
     */
    @ApiOperation(value = "대댓글 작성", notes = "댓글에 대댓글 작성")
    @PostMapping("/api/v3/r")
    public HttpStatus saveReply(@RequestBody Comment comment) {
        commentService.saveReply(comment);
        return HttpStatus.CREATED;
    }


    // 댓글 삭제

    // 댓글 수정
}
