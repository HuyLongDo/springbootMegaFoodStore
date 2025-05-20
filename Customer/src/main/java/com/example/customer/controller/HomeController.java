package com.example.customer.controller;

import com.example.library.model.Customer;
import com.example.library.model.ShoppingCart;
import com.example.library.service.CategoryService;
import com.example.library.service.CustomerService;
import com.example.library.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CustomerService customerService;

    @RequestMapping(value = {"/home","/"}, method = RequestMethod.GET)
    public String home(Model model,
                       Principal principal,
                       HttpSession session){

        //        RestTemplate là gì? tìm hiểu riêng
//        RestTemplate restTemplate = new RestTemplate();
//        String apiUrl = "http://localhost:8080/api/cakes";
//        List<?> cakes = restTemplate.getForObject(apiUrl, List.class);

        model.addAttribute("title", "Cửa Hàng");
        model.addAttribute("page", "Cửa Hàng");
        if(principal != null){
            Customer customer = customerService.findByUsername(principal.getName());
            ShoppingCart cart = customer.getCart();
            session.setAttribute("username", customer.getFirstName() + " " + customer.getLastName());
            if (cart != null) {
                session.setAttribute("totalItems", cart.getTotalItems());
//                session.setAttribute("shoppingCart", cart);
            }
        }
        return "home";
    }

    @GetMapping(value = "/contact")
    public String contact(Model model){
        model.addAttribute("title", "Liên Hệ");
        model.addAttribute("page", "Liên Hệ");
        return "contact-us";
    }

}
