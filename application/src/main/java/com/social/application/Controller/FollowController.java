package com.social.application.Controller;

import com.social.application.Models.Follow;
import com.social.application.Services.FollowService;
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
    public List<Follow>fetchAllFollower(){
        return followService.fetchAllFollow();
    }

    @PostMapping
    public Follow createFollow(@RequestBody Follow follow){
     Follow result = followService.insertFollow(follow);
     return result;
    }

    @GetMapping("/{id}")
    public Follow fetchByFollowId(@PathVariable long id){
        return followService.fetchFollowById(id);
    }
}
