package com.pluralsight.service;

import com.pluralsight.entity.Address;
import com.pluralsight.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class AddressServiceImpl implements AddressService{
    @Autowired
    private AddressRepository addressRepository;
    @Override
    public Address createAddress(Address address) {
        this.addressRepository.save(address);
        return address;
    }
}
