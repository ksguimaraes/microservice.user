package com.example.microservice.user.dto.user;

import java.util.Date;
import com.example.microservice.user.domain.user.status.Status;
import com.example.microservice.user.dto.address.AddressRequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

public record UserRequestDTO(
    String cpf,
    String password,
    String name,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") Date birthDate,
    Status status,
    AddressRequestDTO address
) {    
}
