package com.social.application.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "follows")
public  class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User followerUser;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private User followingUser;

//    private Long followerId;
//    private Long followingId;

    private LocalDateTime createdAt = LocalDateTime.now();

//    public Long getFollowerId() {
//        return followerId;
//    }
//
//    public void setFollowerId(Long followerId) {
//        this.followerId = followerId;
//    }
//
//    public Long getFollowingId() {
//        return followingId;
//    }
//
//    public void setFollowingId(Long followingId) {
//        this.followingId = followingId;
//    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFollowerUser() {
        return followerUser;
    }

    public void setFollowerUser(User followerUser) {
        this.followerUser = followerUser;
    }

    public User getFollowingUser() {
        return followingUser;
    }

    public void setFollowingUser(User followingUser) {
        this.followingUser = followingUser;
    }
}
