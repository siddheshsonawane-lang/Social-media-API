package com.social.application.Controller;

import com.social.application.Models.Comment;
import com.social.application.Services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment>fetchAllComments(){
        return commentService.fetchAllComments();
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment){
        Comment result = commentService.insertComment(comment);
        return result;
    }

    @GetMapping("/{id}")
    public Comment fetchByCommentId(@PathVariable long id){
        return commentService.fetchCommentById(id);
    }
}
