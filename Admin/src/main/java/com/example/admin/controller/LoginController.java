package com.example.admin.controller;

import com.example.library.dto.AdminDto;
import com.example.library.model.Admin;
import com.example.library.model.Role;
import com.example.library.repository.RoleRepository;
import com.example.library.service.impl.AdminServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AdminServiceImpl adminService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    //Khởi tạo tài khoản mặc định
    @PostConstruct
    public void setupDefaultAccount() {
        // Kiểm tra xem đã có tài khoản mặc định hay chưa
        if (adminService.findByUsername("admin@gmail.com") == null) {
            // Tạo tài khoản mặc định
            Admin admin = new Admin();
            admin.setFirstName("Default");
            admin.setLastName("Admin");
            admin.setUsername("admin@gmail.com");
            admin.setEnable(true);
            admin.setPassword(passwordEncoder.encode("123456")); // Mã hóa mật khẩu
            Role AdminRole = roleRepository.findByName("ADMIN");
            if (AdminRole == null) {
                AdminRole = new Role();
                AdminRole.setName("ADMIN");
                roleRepository.save(AdminRole);
            }
            admin.setRoles(Arrays.asList(AdminRole));
            adminService.saveAdmin(admin);
        }else {
            System.out.println("Đã thiết lập tài khoản mặc định");
        }
    }

    @GetMapping(value = {"/login","/"})
    public String loginForm(Model model) {
        model.addAttribute("title", "Login Page");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }

    @PostMapping("/register-new")
    public String addNewEmployee(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                              BindingResult result,
                              Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("adminDto", adminDto);
                result.toString();
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if (admin != null) {
                model.addAttribute("adminDto", adminDto);
                System.out.println("admin account exist ");
                model.addAttribute("emailError", "Your email has been registered!");
                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.saveEmployee(adminDto);
                System.out.println("success");
                model.addAttribute("success", "Register successfully!");
                model.addAttribute("adminDto", adminDto);
            } else {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Your password maybe wrong! Check again!");
                System.out.println("password not same");
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "The server has been wrong!");
        }
        return "register";
    }

}
