package com.social.application.Services;


import com.social.application.Models.Like;
import com.social.application.Models.Post;
import com.social.application.Models.User;
import com.social.application.Repositories.LikeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LikeService {
    private final LikeRepository likeRepository;

    @PersistenceContext
    EntityManager entityManager;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Like insetLike(Like like){
        long userId = like.getUser().getId();
        long postId = like.getPost().getId();

        User userRef = entityManager.getReference(User.class,userId);

        Post postRef = entityManager.getReference(Post.class,postId);

        like.setUser(userRef);
        like.setPost(postRef);
        return likeRepository.save(like);
    }

    public List<Like> fetchAllLike(){
        return likeRepository.findAll();
    }

    public Like fetchLikeById(Long id){
        return likeRepository.findById(id).orElse(null);
    }


}
