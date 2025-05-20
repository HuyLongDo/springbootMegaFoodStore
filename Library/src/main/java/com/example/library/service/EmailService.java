package com.example.library.service;

import com.example.library.model.Customer;

import java.util.List;

public interface EmailService {

//    void sendEmailToCustomer(List<Customer> customerList, String subject, String message);

    void sendThankYouMail(Customer customer);

}
