package com.example.customer.controller;

import com.example.library.model.Customer;
import com.example.library.model.Order;
import com.example.library.model.ShoppingCart;
import com.example.library.service.CustomerService;
import com.example.library.service.OrderService;
import com.example.library.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final CustomerService customerService;
    private final ShoppingCartService cartService;
    private final OrderService orderService;

    //Thanh toán
    @GetMapping("/check-out")
    public String checkOut(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }else{
            Customer customer = customerService.findByUsername(principal.getName());
            if (customer.getAddress() == null ||
                    customer.getCityName() == null ||
                    customer.getPhoneNumber()==null){
                model.addAttribute("customer", customer);
                model.addAttribute("error", " Điền thông tin trước khi thanh toán");
                model.addAttribute("title", "Account");
                model.addAttribute("page", "Account");
                return "account";
            } else {
                ShoppingCart cart = customerService.findByUsername(principal.getName()).getCart();
                model.addAttribute("customer",customer);
                model.addAttribute("shoppingCart", cart);
                model.addAttribute("title", "CheckOut");
                model.addAttribute("page", "CheckOut");
                return "checkout";
            }
        }
    }

    //Trang order sau khi đặt hàng
    @GetMapping("/orders")
    public String OrderPage(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        else {
//            Customer customer = customerService.findByUsername(principal.getName());
//            List<Order> orderList = customer.getOrders();
            List<Order> orderList = orderService.getOrderByUser(principal.getName());
            model.addAttribute("orders", orderList);
            model.addAttribute("title", "Order");
            model.addAttribute("page", "Order");
            return "order";
        }
    }

    //Đã xác nhận thanh toán
    @RequestMapping(value = "/add-order", method = RequestMethod.POST)
    public String saveOrder(@Valid @ModelAttribute("shoppingCart") ShoppingCart cart,
                              Principal principal,
                              Model model,
                              HttpSession session, RedirectAttributes redirectAttributes){
        if (principal == null){
            return "redirect:/login";
        }
        else {
            Customer customer = customerService.findByUsername(principal.getName());
            cart = customer.getCart();
            Order order = orderService.saveOrder(cart);
            session.removeAttribute("totalItems");
            model.addAttribute("order", order);
            model.addAttribute("title", "Order detail");
            model.addAttribute("page", "Order detail");
            model.addAttribute("success", "Đơn hàng của bạn được thanh toán");
            return "order-detail";
        }
    }

    //Hủy đơn nếu nút cancle chưa hiện
    @RequestMapping(value = "/cancel-order/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String cancelOrder(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
//            Order order = orderService.getOrderById(id);
            orderService.cancelOrder(id);
            redirectAttributes.addFlashAttribute("success", "Đã hủy đơn hàng");

        return "redirect:/orders";
    }










}
