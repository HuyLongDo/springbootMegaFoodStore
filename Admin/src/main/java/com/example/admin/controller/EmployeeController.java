package com.example.admin.controller;

import com.example.library.model.Admin;
import com.example.library.model.Role;
import com.example.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EmployeeController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/employees")
    public String employeePage(RedirectAttributes attributes,
                               Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Admin employee= adminService.findByUsername(principal.getName());
        List<String> roles = employee.getRoles().stream()
                .map((role)->role.getName()).collect(Collectors.toList());
        boolean isEmployee = roles.contains("EMPLOYEE");
        if (isEmployee){
            attributes.addFlashAttribute("message","Chỉ Quản Trị Admin mới được vào");
            return "redirect:/index";
        }
        model.addAttribute("title", "Nhân viên");
        List<Admin> adminList = adminService.getEmployeeByRole();
        model.addAttribute("employeeList", adminList);
        model.addAttribute("size", adminList.size());
        return "employees";
    }

    @RequestMapping(value = "/enable-employee/{id}", method = { RequestMethod.GET, RequestMethod.PUT })
    public String enableEmployee(@PathVariable(name = "id") Long employeeId, RedirectAttributes attributes) {
        System.out.println("Enable method is running");
        try {
            boolean isEnable = adminService.enableEmployee(employeeId);
            String message = isEnable ? "Enabled successfully" : "Disabled successfully";
            attributes.addFlashAttribute("success", message);
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Có lỗi xảy ra");
        }
        return "redirect:/employees";
    }

    @RequestMapping(value = "/delete-employee/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteEmployee(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to delete");
        }
        return "redirect:/employees";
    }







}
