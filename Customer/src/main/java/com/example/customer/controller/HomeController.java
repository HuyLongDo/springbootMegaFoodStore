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
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CustomerService customerService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @RequestMapping(value = {"/home","/"}, method = RequestMethod.GET)
    public String home(Model model,
                       Principal principal,
                       HttpSession session){
        model.addAttribute("title", "Home");
        model.addAttribute("page", "Home");
        if(principal != null){
            Customer customer = customerService.findByUsername(principal.getName());
            ShoppingCart cart = customer.getCart();
            session.setAttribute("username", customer.getFirstName() + " " + customer.getLastName());
            if (cart != null) {
                session.setAttribute("totalItems", cart.getTotalItems());
            }
        }
        return "home";
    }

    @GetMapping(value = "/contact")
    public String contact(Model model){
        model.addAttribute("title", "Contact");
        model.addAttribute("page", "Contact");
        return "contact-us";
    }

}
