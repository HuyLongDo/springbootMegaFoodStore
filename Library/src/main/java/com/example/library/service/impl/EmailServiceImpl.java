package com.example.library.service.impl;

import com.example.library.model.Customer;
import com.example.library.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

//    public void sendEmailToCustomer(List<Customer> customerList,
//                                    String subject,
//                                    String message){
//        for (Customer customer: customerList){
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setTo(customer.getUsername());
//            mailMessage.setSubject(subject);
//            mailMessage.setText(message);
//            javaMailSender.send(mailMessage);
//        }
//    }

    @Override
    public void sendThankYouMail(Customer customer) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(customer.getUsername());
            mailMessage.setSubject("Cảm ơn bạn đã thanh toán đơn hàng");
            mailMessage.setText("Xin chào " + customer.getLastName() + ",\n\nCảm ơn bạn đã thanh toán đơn hàng của mình. Chúng tôi rất trân trọng sự ủng hộ của bạn.\n\nDelicacy Cake Store trân trọng,\n phục vụ mọi khách hàng.");
            javaMailSender.send(mailMessage);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
