package com.example.movietwebapplication.Controller;

import com.example.movietwebapplication.APIRespons.ApiResponse;
import com.example.movietwebapplication.DTO.UserDTO;
import com.example.movietwebapplication.Model.User;
import com.example.movietwebapplication.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/movie/user")
public class UserController {
private final UserService userService;


    @PostMapping("/register-user")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody @Valid UserDTO userDTO) {
        userService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User registered successfully."));
    }


    @PutMapping("/update-user")
    public ResponseEntity<ApiResponse> updateUser(@AuthenticationPrincipal User user, @RequestBody @Valid UserDTO userDTO) {
        userService.update(user.getId(), userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User updated successfully."));
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<ApiResponse> deleteUser(@AuthenticationPrincipal User user) {
        userService.delete(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User deleted successfully."));
    }

    //getMyUser
    @GetMapping("/get-my-user")
    public ResponseEntity<UserDTO> getMyUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body( userService.getMyUser(user.getId()));
    }
}
