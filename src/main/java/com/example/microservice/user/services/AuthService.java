package com.example.microservice.user.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.microservice.user.dto.auth.AuthRequestDTO;
import com.example.microservice.user.dto.auth.AuthResponseDTO;
import com.example.microservice.user.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    
    public AuthResponseDTO login(AuthRequestDTO authRequest) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(authRequest.cpf(), authRequest.password());
            Authentication auth = authenticationManager.authenticate(usernamePassword);

            User userDetails = (User) auth.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());

            return new AuthResponseDTO(token);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during authentication");
        }
    }

}