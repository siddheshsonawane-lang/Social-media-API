package com.social.application.Services;


import com.social.application.Models.Comment;
import com.social.application.Models.Post;
import com.social.application.Models.User;
import com.social.application.Repositories.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;



import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;


    @PersistenceContext
    EntityManager entityManager;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;

    }

    public Comment insertComment(Comment comment){

        Long userId = comment.getUser().getId();
        Long postId = comment.getPost().getId();

        User user = entityManager.find(User.class, userId);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        Post post = entityManager.find(Post.class, postId);
        if(post == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        comment.setUser(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    public List<Comment> fetchAllComments(){
        return commentRepository.findAll();
    }


    public Comment fetchCommentById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Comment not found with id: " + id
                        )
                );
    }
}
