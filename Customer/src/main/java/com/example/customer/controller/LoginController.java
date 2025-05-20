package com.example.customer.controller;

import com.example.library.dto.CustomerDto;
import com.example.library.model.Customer;
import com.example.library.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomerService customerService;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("title","Đăng nhập tài khoản");
        model.addAttribute("page", "Đăng nhập tài khoản");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("title","Đăng ký tài khoản");
        model.addAttribute("page", "Đăng ký tài khoản");
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String registerCustomer(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                                   BindingResult result,
                                   Model model ){
        try{
            if(result.hasErrors()){
                model.addAttribute("customerDto", customerDto);
                result.toString();
                return "register";
            }
            Customer customer = customerService.findByUsername(customerDto.getUsername());
            if(customer!=null){
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("error", "Email has been register!");
                System.out.println("Email has been register!");
                return "register";
            }

            if(customerDto.getPassword().equals(customerDto.getConfirmPassword())){
                customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
                customerService.save(customerDto);
                model.addAttribute("success", "Register successfully!");
                System.out.println("Register successfully!");
            }

            else{
                model.addAttribute("customerDto", customerDto);
                model.addAttribute("error", "Incorect password!");
                System.out.println("Incorect password!");
                return "register";
            }
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Server is error, please try again later");
            model.addAttribute("customerDto", customerDto);
        }
        return "register";
    }



}
