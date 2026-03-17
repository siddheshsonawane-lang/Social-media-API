package com.social.application.Services;

import com.social.application.DTOs.CommentDTO;
import com.social.application.DTOs.PostDTO;
import com.social.application.Models.Post;
import com.social.application.Models.User;
import com.social.application.Repositories.LikeRepository;
import com.social.application.Repositories.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @PersistenceContext
    EntityManager entityManager;

    public PostService(PostRepository postRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
    }

    public Post insertPost(Post post){

        Long userId = post.getUser().getId();

        User user = entityManager.find(User.class, userId);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        post.setUser(user);

        return postRepository.save(post);
    }

    public List<PostDTO> getAllPosts() {

        return postRepository.findAll().stream()
                .map(post -> new PostDTO(
                        post.getId(),
                        post.getCaption(),
                        post.getLikes().size(),
                        post.getComments()
                                .stream()
                                .map(c -> new CommentDTO(c.getId(), c.getText()))
                                .toList(),
                        post.getLikes()
                                .stream()
                                .map(l -> l.getUser().getUsername())
                                .toList()
                ))
                .toList();
    }


    public Post fetchPostById(Long id){
        return postRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Post not found with id: " + id
                        )
                );
    }
    public List<PostDTO> getPostsByUserId(Long userId) {
        List <Post> posts = postRepository.findByUser_Id(userId);

        return posts.stream().map(post -> {

            long likeCount = likeRepository.countByPost_Id(post.getId());

            List<CommentDTO> comments =
                    post.getComments()
                            .stream()
                            .map(c -> new CommentDTO(
                                    c.getId(),
                                    c.getText()))
                            .toList();

            List<String> likedBy = post.getLikes()
                    .stream()
                    .map(like -> like.getUser().getUsername())
                    .toList();

            return new PostDTO(
                    (Long) post.getId(),
                    post.getCaption(),
                    likeCount,
                    comments ,likedBy
            );

        }).toList();


    }

    public List<PostDTO> getAllPostsWithCommentsAndLikes() {

        List<Post> posts = postRepository.findAll();

        return posts.stream().map(post -> {

            long likeCount = likeRepository.countByPost_Id(post.getId());

            List<CommentDTO> comments =
                    post.getComments()
                            .stream()
                            .map(c -> new CommentDTO(
                                    c.getId(),
                                    c.getText()))
                            .toList();

            List<String> likedBy = post.getLikes()
                    .stream()
                    .map(like -> like.getUser().getUsername())
                    .toList();


            return new PostDTO(
                    (Long) post.getId(),
                    post.getCaption(),
                    likeCount,
                    comments,likedBy
            );

        }).toList();
    }

}
