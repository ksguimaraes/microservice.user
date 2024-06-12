package com.example.microservice.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.microservice.user.dto.user.UserIdDTO;
import com.example.microservice.user.dto.user.UserRequestDTO;
import com.example.microservice.user.dto.user.UserResponseDTO;
import com.example.microservice.user.dto.user.UserUpdateRequestDTO;
import com.example.microservice.user.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "User", description = "User API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserIdDTO> createUser(@RequestBody UserRequestDTO userRequest, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        String cpfLogged = SecurityContextHolder.getContext().getAuthentication().getName();
        UserIdDTO userIdDto = this.userService.createUser(userRequest, cpfLogged);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userIdDto.userId()).toUri();
        return ResponseEntity.created(uri).body(userIdDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(String userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserUpdateRequestDTO userRequestDto, String userId, HttpServletRequest request) {
        String cpfLogged = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(this.userService.updateUser(userId, userRequestDto, cpfLogged));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(String userId, HttpServletRequest request) {
        String cpfLogged = SecurityContextHolder.getContext().getAuthentication().getName();
        this.userService.deleteUser(userId, cpfLogged);
        return ResponseEntity.noContent().build();
    }
}
