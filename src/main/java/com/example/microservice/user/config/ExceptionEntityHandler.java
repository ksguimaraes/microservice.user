package com.example.microservice.user.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.microservice.user.config.exceptions.BadRequestException;
import com.example.microservice.user.config.exceptions.ConflictException;
import com.example.microservice.user.config.exceptions.NotFoundException;
import com.example.microservice.user.dto.generic.ErrorResponseDTO;

@ControllerAdvice
public class ExceptionEntityHandler {
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleEventNotFound(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity handleAttendeeAlreadyExist(ConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleEventFull(BadRequestException e) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
    }
}
