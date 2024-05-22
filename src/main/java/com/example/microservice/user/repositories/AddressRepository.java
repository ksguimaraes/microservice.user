package com.example.microservice.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservice.user.domain.address.Address;

public interface AddressRepository extends JpaRepository<Address, String>{
    
}