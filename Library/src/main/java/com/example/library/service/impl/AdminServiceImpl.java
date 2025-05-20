package com.example.library.service.impl;

import com.example.library.dto.AdminDto;
import com.example.library.model.Admin;
import com.example.library.model.Role;
import com.example.library.repository.AdminRepository;
import com.example.library.repository.RoleRepository;
import com.example.library.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;

    @Override
    public Admin save(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setEnable(true);
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }


    public Admin saveEmployee(AdminDto adminDto){
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setEnable(false);
        Role employeeRole = roleRepository.findByName("EMPLOYEE");
        if (employeeRole ==null){
            employeeRole = new Role();
            employeeRole.setName("EMPLOYEE");
            roleRepository.save(employeeRole);
        }
        admin.setRoles(Arrays.asList(employeeRole));
        return adminRepository.save(admin);
    }

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public List<Admin> getAllEmployee() {
        return adminRepository.findAll();
    }

    @Override
    public List<Admin> getEmployeeByRole() {
        return adminRepository.getUsersByRole("EMPLOYEE");
    }

    @Override
    public boolean enableEmployee(Long employeeId) {
        Admin existingEmployee = adminRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee with ID: " + employeeId + " not found"));
        boolean employeeStatus = existingEmployee.isEnable();
        existingEmployee.setEnable(!employeeStatus);
        adminRepository.save(existingEmployee);
        return existingEmployee.isEnable();
    }

    @Override
    public void deleteEmployee(Long id) {
        adminRepository.deleteById(id);
    }


}
