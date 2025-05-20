package com.example.customer.controller;

import com.example.library.dto.CustomerDto;
import com.example.library.service.CityService;
import com.example.library.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CityService cityService;

    @GetMapping("/your-profile")
    public String Profile(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        CustomerDto customerDto = customerService.getCustomerDto(username);
        model.addAttribute("customer", customerDto);
        model.addAttribute("title","Profile");
        model.addAttribute("page","Profile");
        return "profile";
    }

    @GetMapping("/account")
    public String accountHome(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        CustomerDto customerDto = customerService.getCustomerDto(username);
        model.addAttribute("customer", customerDto);
        model.addAttribute("title","Account");
        model.addAttribute("page","Account");
        return "account";
    }

    @RequestMapping(value = "/update-profile", method = {RequestMethod.GET, RequestMethod.PUT})
    private String updateAccount(@Valid @ModelAttribute("customer") CustomerDto customerDto,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes,
                                  Model model,
                                  Principal principal){
        if (principal == null){
            return "redirect:/login";
        } else {
            if(result.hasErrors()){
                List<ObjectError> errors = result.getAllErrors();
                for (ObjectError error : errors){
                    redirectAttributes.addFlashAttribute("error", error.getDefaultMessage());
                }
                return "redirect:/account";
            } else {
                try {
                    customerService.update(customerDto);
                    redirectAttributes.addFlashAttribute("success", "Đã cập nhật thông tin");
                    model.addAttribute("customer", customerDto);
                }
                catch (Exception e){
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("error","Error from server");
                }
            }
        }
        return "redirect:/account";
    }








}
