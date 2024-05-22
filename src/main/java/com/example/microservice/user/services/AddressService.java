package com.example.microservice.user.services;

import org.springframework.stereotype.Service;

import com.example.microservice.user.config.exceptions.NotFoundException;
import com.example.microservice.user.domain.address.Address;
import com.example.microservice.user.dto.address.AddressRequestDTO;
import com.example.microservice.user.repositories.AddressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address createAddress(AddressRequestDTO addressRequestDto) {
        Address newAddress = new Address();
        newAddress.setStreet(addressRequestDto.street());
        newAddress.setNumber(addressRequestDto.number());
        newAddress.setComplement(addressRequestDto.complement());
        newAddress.setNeighborhood(addressRequestDto.neighborhood());
        newAddress.setCity(addressRequestDto.city());
        newAddress.setState(addressRequestDto.state());
        newAddress.setZipCode(addressRequestDto.zipCode());

        this.addressRepository.save(newAddress);

        return newAddress;
    }

    public Address updateAddress(String addressId, AddressRequestDTO addressRequestDto) {
        Address address = this.addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("Address not found with id: " + addressId));

        address.setStreet(addressRequestDto.street());
        address.setNumber(addressRequestDto.number());
        address.setComplement(addressRequestDto.complement());
        address.setNeighborhood(addressRequestDto.neighborhood());
        address.setCity(addressRequestDto.city());
        address.setState(addressRequestDto.state());
        address.setZipCode(addressRequestDto.zipCode());

        this.addressRepository.save(address);

        return address;
    }
}
