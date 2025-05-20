//package com.example.admin.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import java.util.Properties;
//
//@Configuration
//@RequiredArgsConstructor
//public class MailConfig {
//
//    public static final String MY_EMAIL = "dohlong1029@gmail.com";
//    public static final String MY_PASSWORD = "Kaido1412102977@";
//
//    public JavaMailSender getJavaMailSender(){
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//
//        mailSender.setUsername(MY_EMAIL);
//        mailSender.setPassword(MY_PASSWORD);
//
//        Properties properties = mailSender.getJavaMailProperties();
//        properties.put("mail.transport.protocol","smtp");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.debug", "true");
//
//        return mailSender;
//    }
//
//
//
//}
