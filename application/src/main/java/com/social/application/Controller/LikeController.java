package com.social.application.Controller;

import com.social.application.Models.Like;
import com.social.application.Services.LikeService;
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
    public List<Like>fetchAllLikes(){
        return likeService.fetchAllLike();
    }

    @PostMapping
    public Like createLike(@RequestBody Like like){
        Like result = likeService.insetLike(like);
        return result;
    }

    @GetMapping("/{id}")
    public Like fetchByLikeId(@PathVariable long id){
        return likeService.fetchLikeById(id);
    }
}
