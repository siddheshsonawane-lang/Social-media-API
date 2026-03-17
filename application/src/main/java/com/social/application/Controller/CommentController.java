package com.social.application.Controller;

import com.social.application.Models.Comment;
import com.social.application.Services.CommentService;
import org.springframework.web.bind.annotation.*;
import com.social.application.Utility.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<Comment>>> fetchAllComments(){

        List<Comment> comments = commentService.fetchAllComments();

        if(comments.isEmpty()){
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("No comments found", "EMPTY_LIST"));
        }

        return ResponseEntity.ok(
                ApiResponse.success("Comments fetched", comments)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Comment>> createComment(@RequestBody Comment comment){

        Comment result = commentService.insertComment(comment);

        return ResponseEntity.status(201)
                .body(ApiResponse.success("Comment created", result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Comment>> fetchByCommentId(@PathVariable long id){

        Comment comment = commentService.fetchCommentById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Comment fetched", comment)
        );
    }
}
