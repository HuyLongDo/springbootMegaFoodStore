package com.example.admin.controller;

import com.example.library.model.Customer;
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
    public String orderPage(Model model, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title","Danh sách đơn hàng");
        List<Order> orderList = orderService.getAllOrder();
        model.addAttribute("orderList",orderList);
        model.addAttribute("size",orderList.size());
        model.addAttribute("orderNew", new Order());
        return "orders";
    }

    @GetMapping("/orders-cancel")
    public String orderByCancel(Model model, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title","Danh sách đơn hàng hủy");
        List<Order> orderList = orderService.getAllOrderByCancel();
        model.addAttribute("orderList",orderList);
        model.addAttribute("size",orderList.size());
        model.addAttribute("orderNew", new Order());
        return "orders";
    }

    @GetMapping("/orders-accept")
    public String orderByAccept(Model model, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title","Danh sách đơn hàng xác nhận");
        List<Order> orderList = orderService.getAllOrderByAccept();
        model.addAttribute("orderList",orderList);
        model.addAttribute("size",orderList.size());
        model.addAttribute("orderNew", new Order());
        return "orders";
    }

    @GetMapping("/orders-waiting")
    public String orderByWaiting(Model model, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title","Danh sách đơn hàng chờ");
        List<Order> orderList = orderService.getAllOrderByWaiting();
        model.addAttribute("orderList",orderList);
        model.addAttribute("size",orderList.size());
        model.addAttribute("orderNew", new Order());
        return "orders";
    }

    @GetMapping("/order-detail/{id}")
    public String orderDetailPage(@PathVariable("id") Long id, Model model, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }
        Order order = orderService.getOrderById(id);
        Customer customer = order.getCustomer();
        model.addAttribute("title","Chi tiết đơn hàng");
        model.addAttribute("order",order);
        model.addAttribute("customer", customer);
        return "order-details";
    }

    @RequestMapping(value = "/update-order", params = "action=accept",method = {RequestMethod.POST, RequestMethod.PUT})
    public String acceptOrder(@RequestParam("id") Long id,
                              @RequestParam("shippingFee") double shippingFee,
                              RedirectAttributes redirectAttributes,
                              Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }else {
            Order order = orderService.getOrderById(id);
            orderService.acceptOrder(order, shippingFee);
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
            redirectAttributes.addFlashAttribute("success", "Đã hủy đơn");
            return "redirect:/orders";
        }
    }

    @RequestMapping(value = "/update-order", params = "action=delete",method = {RequestMethod.POST, RequestMethod.PUT})
    public String deleteOrder(@RequestParam("id") Long id,
                              RedirectAttributes redirectAttributes,
                              Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }else {
            orderService.deleteOrder(id);
            redirectAttributes.addFlashAttribute("success", "Đã Xóa đơn hàng");
            return "redirect:/orders";
        }
    }







}
