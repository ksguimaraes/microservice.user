package com.example.microservice.user.dto.address;

import java.util.Optional;

public record AddressUpdateRequestDTO(
    Optional<String> street,
    Optional<Integer> number,
    Optional<String> complement,
    Optional<String> neighborhood,
    Optional<String> city,
    Optional<String> state,
    Optional<String> zipCode) 
{
}