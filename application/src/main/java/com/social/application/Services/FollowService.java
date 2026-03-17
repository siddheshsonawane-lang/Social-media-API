package com.social.application.Services;


import com.social.application.Models.Follow;
import com.social.application.Models.User;
import com.social.application.Repositories.FollowRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


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

        Long followerId = follow.getFollowerUser().getId();
        Long followingId = follow.getFollowingUser().getId();

        User follower = entityManager.find(User.class, followerId);
        if(follower == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Follower user not found");
        }

        User following = entityManager.find(User.class, followingId);
        if(following == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Following user not found");
        }

        // optional but important sanity check
        if(followerId.equals(followingId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User cannot follow themselves");
        }

        follow.setFollowerUser(follower);
        follow.setFollowingUser(following);

        return followRepository.save(follow);
    }

    public List<Follow> fetchAllFollow(){
        return followRepository.findAll();
    }

    public Follow fetchFollowById(Long id){
        return followRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Follow not found with id: " + id
                        )
                );
    }


}
