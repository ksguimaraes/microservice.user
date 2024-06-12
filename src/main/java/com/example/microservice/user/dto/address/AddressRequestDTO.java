package com.example.microservice.user.dto.address;

import java.util.Optional;

public record AddressRequestDTO(
    String street,
    Integer number,
    Optional<String> complement,
    String neighborhood,
    String city,
    String state,
    String zipCode) 
{
}