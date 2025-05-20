package com.example.library.dto;

import com.example.library.model.City;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @Size(min = 3, max = 10, message = "Nickname cần 3 tới 10 ký tự")
    private String firstName;

    @Size(min = 3, max = 10, message = "Tên cần 3 tới 10 ký tự")
    private String lastName;

    private String username;

//    @Size(min = 3, max = 15, message = "Password cần 3 tới 15 ký tự")
    private String password;

    @Size(min = 10, max = 15, message = "Số điện thoại cần 10 tới 15 ký tự")
    @Pattern(regexp = "\\d+", message = "Số điện thoại chỉ được chứa chữ số.")
    private String phoneNumber;

    private String address;
    private String confirmPassword;
    private String cityName;
    private City city;
}
