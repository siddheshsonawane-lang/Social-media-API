package com.social.application.Services;

import com.social.application.DTOs.UserDTO;
import com.social.application.Models.User;
import com.social.application.Repositories.FollowRepository;
import com.social.application.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


import java.util.List;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, FollowRepository followRepository ,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User insertUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public List<UserDTO> fetchAllUser(){

            List<User> users = userRepository.findAll();

            return users.stream().map(user -> {

                long followers = followRepository.countByFollowerUser_Id(user.getId());
                long following = followRepository.countByFollowingUser_Id(user.getId());

                return new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getBio(),
                        user.getProfileImage(),
                        followers,
                        following
                );

            }).toList();
        }

    public User fetchUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found with id: " + id
                        )
                );
    }


    public UserDTO getUserDTO(Long userId) {
        User user = fetchUserById(userId);

        long followers = followRepository.countByFollowingUser_Id(userId);
        long following = followRepository.countByFollowerUser_Id(userId);

        return new UserDTO(
                userId,
                user.getUsername(),
                user.getBio(),
                user.getProfileImage(),
                followers,
                following
        );
    }
}
