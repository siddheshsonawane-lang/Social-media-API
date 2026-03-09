package com.social.application.Services;


import com.social.application.Models.Follow;
import com.social.application.Models.User;
import com.social.application.Repositories.FollowRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FollowService {
    private final FollowRepository followRepository;

    @PersistenceContext
    EntityManager entityManager;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public Follow insertFollow(Follow follow){
        long followerId = follow.getFollowerUser().getId();
        long followingId = follow.getFollowingUser().getId();

        User followerRef = entityManager.getReference(User.class,followerId);
        User followingRef = entityManager.getReference(User.class,followingId);

        follow.setFollowerUser(followerRef);
        follow.setFollowingUser(followingRef);
        return followRepository.save(follow);
    }

    public List<Follow> fetchAllFollow(){
        return followRepository.findAll();
    }

    public Follow fetchFollowById(Long id){
        return followRepository.findById(id).orElse(null);
    }


}
