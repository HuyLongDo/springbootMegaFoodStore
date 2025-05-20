package com.example.admin.controller;

import com.example.library.model.Admin;
import com.example.library.service.impl.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AdminServiceImpl adminService;

    @RequestMapping("/index")
    public String home(Model model, Principal principal, RedirectAttributes attributes) {
        model.addAttribute("title", "Home Page");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//            return "redirect:/login";
//        }
        if (principal == null) {
            return "redirect:/login";
        }
        Admin employee= adminService.findByUsername(principal.getName());
        if (employee.isEnable()==false){
            attributes.addFlashAttribute("message","Bạn chưa được Admin duyệt");
            return "redirect:/login";
        }

        return "index";
    }

    @RequestMapping("/indexPage")
    public String ReturnIndexPage(Model model) {
        model.addAttribute("title", "Home Page");
        return "index";
    }




}
