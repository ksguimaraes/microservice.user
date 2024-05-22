package com.example.microservice.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.microservice.user.dto.user.UserIdDTO;
import com.example.microservice.user.dto.user.UserRequestDTO;
import com.example.microservice.user.dto.user.UserResponseDTO;
import com.example.microservice.user.services.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "User", description = "User API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserIdDTO> createUser(@RequestBody UserRequestDTO userRequest, UriComponentsBuilder uriBuilder) {
        System.out.println("Received request: " + userRequest);
        UserIdDTO userIdDto = this.userService.createUser(userRequest);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userIdDto.userId()).toUri();
        return ResponseEntity.created(uri).body(userIdDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(String userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserRequestDTO userRequestDto, String userId) {
        return ResponseEntity.ok(this.userService.updateUser(userId, userRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(String userId) {
        this.userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
