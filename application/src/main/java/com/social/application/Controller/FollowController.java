package com.social.application.Controller;

import com.social.application.Models.Follow;
import com.social.application.Services.FollowService;
import com.social.application.Utility.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/follow")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Follow>>> fetchAllFollower(){

        List<Follow> follows = followService.fetchAllFollow();

        return ResponseEntity.ok(
                ApiResponse.success("Follows fetched", follows)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Follow>> createFollow(@RequestBody Follow follow){

        Follow result = followService.insertFollow(follow);

        return ResponseEntity.status(201)
                .body(ApiResponse.success("Follow created", result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Follow>> fetchByFollowId(@PathVariable long id){

        Follow follow = followService.fetchFollowById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Follow fetched", follow)
        );
    }
}
