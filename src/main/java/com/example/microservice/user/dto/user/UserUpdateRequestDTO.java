package com.example.microservice.user.dto.user;

import java.util.Date;
import java.util.Optional;
import com.example.microservice.user.domain.user.status.Status;
import com.example.microservice.user.dto.address.AddressUpdateRequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

public record UserUpdateRequestDTO(
    Optional<String> cpf,
    Optional<String> password,
    Optional<String> name,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") Optional<Date> birthDate,
    Optional<Status> status,
    Optional<AddressUpdateRequestDTO> address
) {
}
