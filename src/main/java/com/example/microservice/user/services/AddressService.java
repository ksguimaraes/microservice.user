package com.example.microservice.user.services;

import org.springframework.stereotype.Service;

import com.example.microservice.user.config.exceptions.NotFoundException;
import com.example.microservice.user.domain.address.Address;
import com.example.microservice.user.dto.address.AddressRequestDTO;
import com.example.microservice.user.dto.address.AddressUpdateRequestDTO;
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
        addressRequestDto.complement().ifPresent(newAddress::setComplement);
        newAddress.setNeighborhood(addressRequestDto.neighborhood());
        newAddress.setCity(addressRequestDto.city());
        newAddress.setState(addressRequestDto.state());
        newAddress.setZipCode(addressRequestDto.zipCode());

        this.addressRepository.save(newAddress);

        return newAddress;
    }

    public Address updateAddress(String addressId, AddressUpdateRequestDTO addressRequestDto) {
        Address address = this.addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("Address not found with id: " + addressId));
    
        addressRequestDto.street().ifPresent(address::setStreet);
        addressRequestDto.number().ifPresent(address::setNumber);
        addressRequestDto.complement().ifPresent(address::setComplement);
        addressRequestDto.neighborhood().ifPresent(address::setNeighborhood);
        addressRequestDto.city().ifPresent(address::setCity);
        addressRequestDto.state().ifPresent(address::setState);
        addressRequestDto.zipCode().ifPresent(address::setZipCode);
    
        this.addressRepository.save(address);
    
        return address;
    }    
}
