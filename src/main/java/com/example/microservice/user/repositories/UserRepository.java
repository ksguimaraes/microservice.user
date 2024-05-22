package com.example.microservice.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservice.user.domain.user.User;

public interface UserRepository extends JpaRepository<User, String>{
    Optional<User> findByCpf(String cpf);  
}