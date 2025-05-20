package com.example.library.service;

import com.example.library.dto.AdminDto;
import com.example.library.model.Admin;

import java.util.List;

public interface AdminService {
    Admin save(AdminDto adminDto);

    Admin saveAdmin(Admin admin);

    Admin findByUsername(String username);

    List<Admin> getAllEmployee();

    List<Admin> getEmployeeByRole();

    boolean enableEmployee(Long employeeId);

    void deleteEmployee(Long id);

}
