package com.example.admin.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private static final String PATH ="/error";

    @RequestMapping(value = PATH)
    public String handle404Error() {
        return "404"; // Trả về tên của trang 404
    }

    public String getErrorPath() {
        return PATH;
    }
}
