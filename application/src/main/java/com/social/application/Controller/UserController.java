package com.social.application.Controller;
import com.social.application.DTOs.LoginRequestDTO;
import com.social.application.DTOs.UserDTO;
import com.social.application.Models.User;
import com.social.application.Services.AuthService;
import com.social.application.Services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping
    public List<UserDTO> fetchAllUser() {
        return userService.fetchAllUser();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        User result = userService.insertUser(user);
        return result;
    }

    @GetMapping("/{id}")
    public User fetchByUserId(@PathVariable long id) {
        return userService.fetchUserById(id);
    }

    @GetMapping("/{id}/stats")
    public UserDTO getUserStats(@PathVariable long id) {
        return userService.getUserDTO(id);
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);


    }
}
