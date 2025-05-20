//package com.example.customer.controller;
//
//import com.example.library.model.Customer;
//import com.example.library.repository.CustomerRepository;
//import com.example.library.service.EmailService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//public class MailController {
//
//    private final EmailService emailService;
//    private final CustomerRepository customerRepository;
//
//    //Test gửi nhiều khách
//    @PostMapping("/send-email")
//    public void sendEmailToCustomer(){
//        List<Customer> customers = customerRepository.findAll();
//        String subject = "Thông Báo Đơn Hàng";
//        String message = "Xin chào, đơn hàng bạn đã được xử lý. Cảm ơn đã ủng hộ Delicary Cake Store!";
//        emailService.sendEmailToCustomer(customers, subject, message);
//    }
//
////    //Gửi cho 1 khách
////    @PostMapping("/send-mail-payment")
////    public void processPayment(@RequestBody PaymentRequest paymentRequest) {
////        // Xử lý thanh toán và lưu đơn hàng vào CSDL
////
////        // Lấy thông tin khách hàng từ đơn hàng
////        Customer customer = customerRepository.findById(paymentRequest.getCustomerId()).orElse(null);
////        if (customer != null) {
////            emailService.sendThankYouMail(customer);
////        }
////    }
//
//
//
//
//
//
//}
