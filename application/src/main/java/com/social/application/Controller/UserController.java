package com.social.application.Controller;
import com.social.application.DTOs.LoginRequestDTO;
import com.social.application.DTOs.UserDTO;
import com.social.application.Models.User;
import com.social.application.Services.AuthService;
import com.social.application.Services.UserService;
import com.social.application.Utility.ApiResponse;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<List<UserDTO>>> fetchAllUser() {

        List<UserDTO> users = userService.fetchAllUser();

        return ResponseEntity.ok(
                ApiResponse.success("Users fetched", users)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody User user) {

        User saved = userService.insertUser(user);

        UserDTO dto = userService.getUserDTO(saved.getId());

        return ResponseEntity.status(201)
                .body(ApiResponse.success("User created", dto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> fetchByUserId(@PathVariable long id) {

        UserDTO user = userService.getUserDTO(id);

        return ResponseEntity.ok(
                ApiResponse.success("User fetched", user)
        );
    }


    @GetMapping("/{id}/stats")
    public UserDTO getUserStats(@PathVariable long id) {
        return userService.getUserDTO(id);
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDTO>> login(@RequestBody LoginRequestDTO request) {

        User user = authService.login(request);

        UserDTO dto = userService.getUserDTO(user.getId());

        return ResponseEntity.ok(
                ApiResponse.success("Login successful", dto)
        );
    }


    @GetMapping("/reponse/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable long id) {

        User user = userService.fetchUserById(id);

        if(user == null){
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("User not found","USER_NOT_FOUND"));
        }

        return ResponseEntity.ok(
                ApiResponse.success("User fetched", user)
        );
    }
}
