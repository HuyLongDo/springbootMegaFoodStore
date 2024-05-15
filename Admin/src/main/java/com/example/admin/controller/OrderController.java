package com.example.admin.controller;

import com.example.library.model.Order;
import com.example.library.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public String getAllOrder(Model model, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }else {
            List<Order> orderList = orderService.getAllOrder();
            model.addAttribute("orderList",orderList);
            return "orders";
        }
    }

    @RequestMapping(value = "/update-order", params = "action=accept",method = {RequestMethod.POST, RequestMethod.PUT})
    public String acceptOrder(@RequestParam("id") Long id,
                              RedirectAttributes redirectAttributes,
                              Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }else {
            Order order = orderService.getOrderById(id);
            orderService.acceptOrder(order);
            redirectAttributes.addFlashAttribute("success", "Đã chấp nhận order");
            return "redirect:/orders";
        }
    }

    @RequestMapping(value = "/update-order", params = "action=cancel",method = {RequestMethod.POST, RequestMethod.PUT})
    public String cancelOrder(@RequestParam("id") Long id,
                              RedirectAttributes redirectAttributes,
                              Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }else {
            orderService.cancelOrder(id);
            redirectAttributes.addFlashAttribute("success", "Đã từ chối đơn hàng");
            return "redirect:/orders";
        }
    }







}
