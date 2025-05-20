package com.example.library.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
    @Size(min = 3, max = 10, message = "Cần 3-10 ký tự")
    private String firstName;
    @Size(min = 3, max = 10, message = "Tên Cần 3 tới 10 ký tự")
    private String lastName;
    private String username;
//    @Size(min = 5, max = 10, message = "Password chứa 5 tới 10 ký tự")
    private String password;
    private String repeatPassword;
    private boolean enable;
}

