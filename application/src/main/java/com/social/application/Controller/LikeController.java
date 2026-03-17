package com.social.application.Controller;

import com.social.application.Models.Like;
import com.social.application.Services.LikeService;
import com.social.application.Utility.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Like>>> fetchAllLikes() {

        List<Like> likes = likeService.fetchAllLike();

        return ResponseEntity.ok(
                ApiResponse.success("Likes fetched", likes)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Like>> createLike(@RequestBody Like like) {

        Like result = likeService.insertLike(like);

        return ResponseEntity.status(201)
                .body(ApiResponse.success("Like created", result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Like>> fetchByLikeId(@PathVariable long id) {

        Like like = likeService.fetchLikeById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Like fetched", like)
        );
    }
}
