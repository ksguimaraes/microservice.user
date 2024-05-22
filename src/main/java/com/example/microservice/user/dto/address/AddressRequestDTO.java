package com.example.microservice.user.dto.address;

public record AddressRequestDTO(
    String street,
    Integer number,
    String complement,
    String neighborhood,
    String city,
    String state,
    String zipCode) 
{
}