package com.example.library.service;

import com.example.library.dto.CustomerDto;
import com.example.library.model.Customer;

public interface CustomerService {

    Customer findByUsername(String username);

    Customer findById(Long id);

    Customer save(CustomerDto customerDto);

    CustomerDto getCustomerDto(String username);

    Customer update(CustomerDto customerDto);

    Customer updateInfoCustomerGuest(CustomerDto customerDto);

    Customer saveCustomerGuest(CustomerDto customerDto);
}
