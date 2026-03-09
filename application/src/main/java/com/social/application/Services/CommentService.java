package com.social.application.Services;


import com.social.application.Models.Comment;
import com.social.application.Models.Post;
import com.social.application.Models.User;
import com.social.application.Repositories.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

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
       long userId = comment.getUser().getId();
       long postId = comment.getPost().getId();
       User userRef = entityManager.getReference(User.class,userId);

       Post postRef = entityManager.getReference(Post.class,postId);

       comment.setUser(userRef);
       comment.setPost(postRef);
        return commentRepository.save(comment);
    }

    public List<Comment> fetchAllComments(){
        return commentRepository.findAll();
    }

    public Comment fetchCommentById(Long id){
        return commentRepository.findById(id).orElse(null);
    }
}
