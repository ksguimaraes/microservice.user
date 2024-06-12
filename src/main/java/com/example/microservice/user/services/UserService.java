package com.example.microservice.user.services;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.microservice.user.config.exceptions.ConflictException;
import com.example.microservice.user.config.exceptions.NotFoundException;
import com.example.microservice.user.domain.address.Address;
import com.example.microservice.user.domain.user.User;
import com.example.microservice.user.domain.user.status.Status;
import com.example.microservice.user.dto.user.UserResponseDTO;
import com.example.microservice.user.dto.user.UserUpdateRequestDTO;
import com.example.microservice.user.dto.user.UserIdDTO;
import com.example.microservice.user.dto.user.UserRequestDTO;
import com.example.microservice.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;
    
    public UserIdDTO createUser(UserRequestDTO userRequestDto, String cpfLogged) {
        if(this.userRepository.findByCpf(userRequestDto.cpf()).isPresent()) {
            throw new ConflictException("User already exists with cpf: " + userRequestDto.cpf());
        }
        Address address = this.addressService.createAddress(userRequestDto.address());
        Optional<User> userLogged = this.userRepository.findByCpf(cpfLogged);

        User newUser = new User();
        newUser.setCpf(userRequestDto.cpf());
        newUser.setPassword(passwordEncoder.encode(userRequestDto.password()));
        newUser.setName(userRequestDto.name());
        newUser.setBirthDate(userRequestDto.birthDate());
        newUser.setStatus(userRequestDto.status());
        newUser.setAddress(address);
        newUser.setCreatedByUser(userLogged.orElse(null));
        
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
            user.getCreatedByUser() != null ? user.getCreatedByUser().getId() : null,
            user.getUpdatedByUser() != null ?  user.getUpdatedByUser().getId() : null,
            user.getDeletedByUser() != null ? user.getDeletedByUser().getId() : null
        );
    }

    public UserResponseDTO updateUser(String userId, UserUpdateRequestDTO userRequestDto, String cpfLogged) {
        User existingUser = this.userRepository.findById(userId).orElseThrow(() ->
            new NotFoundException("User not found with id: " + userId)
        );
    
        if (userRequestDto.cpf().isPresent()) {
            String cpf = userRequestDto.cpf().get();
            Optional<User> userWithCpfOptional = this.userRepository.findByCpf(cpf);
            if (userWithCpfOptional.isPresent() && !userWithCpfOptional.get().getId().equals(userId)) {
                throw new ConflictException("User already exists with cpf: " + cpf);
            }
            existingUser.setCpf(cpf);
        }
    
        Optional<User> userLogged = this.userRepository.findByCpf(cpfLogged);
    
        if (userRequestDto.address().isPresent()) {
            Address updatedAddress = this.addressService.updateAddress(existingUser.getAddress().getId(), userRequestDto.address().get());
            existingUser.setAddress(updatedAddress);
        }
    
        userRequestDto.password().ifPresent(existingUser::setPassword);
        userRequestDto.name().ifPresent(existingUser::setName);
        userRequestDto.birthDate().ifPresent(existingUser::setBirthDate);
        userRequestDto.status().ifPresent(existingUser::setStatus);
    
        existingUser.setUpdatedAt(new Date());
        existingUser.setUpdatedByUser(userLogged.orElse(null));
    
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
            existingUser.getCreatedByUser() != null ? existingUser.getCreatedByUser().getId() : null,
            existingUser.getUpdatedByUser() != null ? existingUser.getUpdatedByUser().getId() : null,
            existingUser.getDeletedByUser() != null ? existingUser.getDeletedByUser().getId() : null
        );
    }   

    public void deleteUser(String userId, String cpfLogged) {
        User existingUser = this.userRepository.findById(userId).orElseThrow(() -> 
            new NotFoundException("User not found with id: " + userId)
        );
        Optional<User> userLogged = this.userRepository.findByCpf(cpfLogged);

        existingUser.setDeletedAt(new Date());
        existingUser.setStatus(Status.Inativo);
        existingUser.setDeletedByUser(userLogged.orElse(null));
    
        this.userRepository.save(existingUser);
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        try {
            Optional<User> optionalUser = this.userRepository.findByCpf(cpf);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                return new org.springframework.security.core.userdetails.User(
                        user.getCpf(),
                        user.getPassword(),
                        Collections.emptyList());
            } else {
                throw new NotFoundException("User not found with cpf: " + cpf);
            }
        } catch (NotFoundException ex) {
            throw new UsernameNotFoundException("User not found with cpf: " + cpf, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException("Unexpected error during user retrieval", ex);
        }
    }
}