package com.social.application.Repositories;

import com.social.application.Models.Follow;
import com.social.application.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    long countByFollowerUser_Id(Long id);
    long countByFollowingUser_Id(Long id);
}
