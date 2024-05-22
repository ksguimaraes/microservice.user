package com.example.microservice.user.dto.user;

import java.util.Date;

import com.example.microservice.user.domain.address.Address;
import com.example.microservice.user.domain.user.status.Status;

public record UserResponseDTO(
    String id,
    String cpf,
    String name,
    Date birthDate,
    Status status,
    Address address,
    Date createdAt,
    Date updatedAt,
    Date deletedAt,
    String createdByUserId,
    String updatedByUserId,
    String deletedByUserId    
) {
}
