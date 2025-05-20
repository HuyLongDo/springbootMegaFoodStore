package com.example.library.service.impl;

import com.example.library.dto.CustomerDto;
import com.example.library.model.Customer;
import com.example.library.repository.CustomerRepository;
import com.example.library.repository.RoleRepository;
import com.example.library.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public Customer save(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPassword(customerDto.getPassword());
        customer.setUsername(customerDto.getUsername());
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        return customerRepository.save(customer);
    }

    @Override
    public Customer saveCustomerGuest(CustomerDto customerDto) {
        Customer customerEx = new Customer();
        customerEx.setLastName(customerDto.getLastName());
        customerEx.setPhoneNumber(customerDto.getPhoneNumber());
        customerEx.setAddress(customerDto.getAddress());
        customerEx.setCity(customerDto.getCity());
        customerEx.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        return customerRepository.save(customerEx);
    }

    @Override
    public Customer update(CustomerDto customerDto){
        Customer customer = customerRepository.findByUsername(customerDto.getUsername());
            customer.setFirstName(customerDto.getFirstName());
            customer.setLastName(customerDto.getLastName());
            customer.setAddress(customerDto.getAddress());
            customer.setCityName(customerDto.getCityName());
            customer.setPhoneNumber(customerDto.getPhoneNumber());
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateInfoCustomerGuest(CustomerDto customerDto) {
        Customer customerNew = new Customer();
        customerNew.setLastName(customerDto.getLastName());
        customerNew.setAddress(customerDto.getAddress());
        customerNew.setPhoneNumber(customerDto.getPhoneNumber());
        return customerNew;
    }

    @Override
    public CustomerDto getCustomerDto(String username) {
        CustomerDto customerDto = new CustomerDto();
        Customer customer = customerRepository.findByUsername(username);
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setUsername(customer.getUsername());
        customerDto.setPassword(customer.getPassword());
        customerDto.setAddress(customer.getAddress());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setCityName(customer.getCityName());
        customerDto.setCity(customer.getCity());
        return customerDto;
    }


}
