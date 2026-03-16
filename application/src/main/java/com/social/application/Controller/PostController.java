package com.social.application.Controller;

import com.social.application.DTOs.PostDTO;
import com.social.application.Models.Post;
import com.social.application.Models.User;
import com.social.application.Services.PostService;
import com.social.application.Utility.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostDTO>getAllPosts(){
        return postService.getAllPostsWithCommentsAndLikes();
    }

    @PostMapping
    public Post createPost(@RequestBody Post post){
        Post result = postService.insertPost(post);
        return result;
    }

    @GetMapping("/{id}")
    public Post fetchByPostId(@PathVariable long id){
        return postService.fetchPostById(id);
    }

    //this is get mapping for userid
    @GetMapping("/user/{userId}")
    public List<PostDTO> getPostsByUserId(@PathVariable Long userId) {
        return postService.getPostsByUserId(userId);
    }

    @GetMapping("/postWithComment")
    public List<PostDTO>getPostsWithComments(){
        return postService.getAllPostsWithCommentsAndLikes();
    }


    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getPosts() {
        List<PostDTO> posts = postService.getAllPosts();

        return ResponseEntity.ok(
                ApiResponse.success("Posts fetched successfully", posts)
        );
    }

//    @GetMapping
//    public ResponseEntity<ApiResponse<List<PostDTO>>> getPosts(){
//
//        List<PostDTO> posts = postService.getAllPosts();
//
//        return ResponseEntity.ok(
//                ApiResponse.success("Posts fetched successfully", posts)
//        );
//    }
}
