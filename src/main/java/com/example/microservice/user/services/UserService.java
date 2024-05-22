package com.example.microservice.user.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.microservice.user.config.exceptions.ConflictException;
import com.example.microservice.user.config.exceptions.NotFoundException;
import com.example.microservice.user.domain.address.Address;
import com.example.microservice.user.domain.user.User;
import com.example.microservice.user.domain.user.status.Status;
import com.example.microservice.user.dto.user.UserResponseDTO;
import com.example.microservice.user.dto.user.UserIdDTO;
import com.example.microservice.user.dto.user.UserRequestDTO;
import com.example.microservice.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressService addressService;
    
    public UserIdDTO createUser(UserRequestDTO userRequestDto) {
        if(this.userRepository.findByCpf(userRequestDto.cpf()).isPresent()) {
            throw new ConflictException("User already exists with cpf: " + userRequestDto.cpf());
        }
        Address address = this.addressService.createAddress(userRequestDto.address());

        User newUser = new User();
        newUser.setCpf(userRequestDto.cpf());
        newUser.setPassword(userRequestDto.password());
        newUser.setName(userRequestDto.name());
        newUser.setBirthDate(userRequestDto.birthDate());
        newUser.setStatus(userRequestDto.status());
        newUser.setAddress(address);
        
        this.userRepository.save(newUser);

        return new UserIdDTO(newUser.getId());
    }

    public UserResponseDTO getUserById(String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        
        return new UserResponseDTO(
            user.getId(),
            user.getCpf(),
            user.getName(),
            user.getBirthDate(),
            user.getStatus(),
            user.getAddress(),
            user.getCreatedAt(),
            user.getUpdatedAt(),
            user.getDeletedAt(),
            user.getCreatedByUser().getId(),
            user.getUpdatedByUser().getId(),
            user.getDeletedByUser().getId()
        );
    }

    public UserResponseDTO updateUser(String userId, UserRequestDTO userRequestDto) {
        User existingUser = this.userRepository.findById(userId).orElseThrow(() -> 
            new NotFoundException("User not found with id: " + userId)
        );
    
        Optional<User> userWithCpfOptional = this.userRepository.findByCpf(userRequestDto.cpf());
        if (userWithCpfOptional.isPresent() && !userWithCpfOptional.get().getId().equals(userId)) {
            throw new ConflictException("User already exists with cpf: " + userRequestDto.cpf());
        }
    
        Address updatedAddress = this.addressService.updateAddress(existingUser.getAddress().getId(), userRequestDto.address());
    
        existingUser.setCpf(userRequestDto.cpf());
        existingUser.setPassword(userRequestDto.password());
        existingUser.setName(userRequestDto.name());
        existingUser.setBirthDate(userRequestDto.birthDate());
        existingUser.setStatus(userRequestDto.status());
        existingUser.setAddress(updatedAddress);
        existingUser.setUpdatedAt(new Date());
    
        this.userRepository.save(existingUser);
        
        return new UserResponseDTO(
            existingUser.getId(),
            existingUser.getCpf(),
            existingUser.getName(),
            existingUser.getBirthDate(),
            existingUser.getStatus(),
            existingUser.getAddress(),
            existingUser.getCreatedAt(),
            existingUser.getUpdatedAt(),
            existingUser.getDeletedAt(),
            existingUser.getCreatedByUser().getId(),
            existingUser.getUpdatedByUser().getId(),
            existingUser.getDeletedByUser().getId()
        );
    }

    public void deleteUser(String userId) {
        User existingUser = this.userRepository.findById(userId).orElseThrow(() -> 
            new NotFoundException("User not found with id: " + userId)
        );
    
        existingUser.setDeletedAt(new Date());
        existingUser.setStatus(Status.Inativo);
    
        this.userRepository.save(existingUser);
    }
}