package com.example.customer.controller;

import com.example.library.dto.CustomerDto;
import com.example.library.model.Customer;
import com.example.library.model.Discount;
import com.example.library.model.Order;
import com.example.library.model.ShoppingCart;
import com.example.library.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final DiscountService discountService;
    private final EmailService emailService;

    //Thanh toán
    @GetMapping("/check-out")
    public String checkOut(Model model, Principal principal,
                           HttpSession session){
        if (principal != null){
            CustomerDto customerDto = customerService.getCustomerDto(principal.getName());
            if (customerDto.getAddress() == null ||
                    customerDto.getCityName() == null ||
                    customerDto.getPhoneNumber()==null){
                model.addAttribute("customer", customerDto);
                model.addAttribute("error", " Điền thông tin trước khi thanh toán");
                model.addAttribute("title", "Account");
                model.addAttribute("page", "Account");
                return "account";
            } else {
                ShoppingCart cart = customerService.findByUsername(principal.getName()).getCart();
                model.addAttribute("customer",customerDto);
                model.addAttribute("shoppingCart", cart);
                model.addAttribute("title", "CheckOut");
                model.addAttribute("page", "CheckOut");
                return "checkout";
            }
        }else{
            CustomerDto customerGuest;
            ShoppingCart cartGuest = (ShoppingCart) session.getAttribute("shoppingCart");
            if (cartGuest == null){
                return "redirect:/cart";
            }
            customerGuest = (CustomerDto) session.getAttribute("customer");
            if (customerGuest == null){
                customerGuest = new CustomerDto();
            }
            session.setAttribute("customer", customerGuest);
            session.setAttribute("shoppingCart", cartGuest);
            model.addAttribute("customer", customerGuest);
            model.addAttribute("shoppingCart", cartGuest);
            model.addAttribute("title", "CheckOut");
            model.addAttribute("page", "CheckOut");
            return "checkout";
        }
    }

    //Trang order sau khi đặt hàng
    @GetMapping("/orders")
    public String orderPage(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        else {
            List<Order> orderList = orderService.getOrderByUser(principal.getName());
            model.addAttribute("orders", orderList);
            model.addAttribute("title", "Order");
            model.addAttribute("page", "Order");
            return "order";
        }
    }

    //Load trang order-detail
    @GetMapping("/order-detail/{id}")
    public String orderDetailPage(@PathVariable("id") Long id,
                                  Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("title", "Order Detail");
        model.addAttribute("page", "Order Detail");
        return "order-detail";
    }

    //Đã xác nhận thanh toán
    @RequestMapping(value = "/add-order", method = RequestMethod.POST)
    public String saveOrder(Principal principal,
                              Model model, RedirectAttributes redirectAttributes,
                              HttpSession session){
        if (principal != null){
            if (session.getAttribute("totalItems") == null){
                return "home";
            }
            Customer customer = customerService.findByUsername(principal.getName());
            ShoppingCart cart = customer.getCart();
            if (session.getAttribute("totalPrice") != null){
                double totalPrice = (double) session.getAttribute("totalPrice");
                cart.setTotalPrice(totalPrice);
            }
            Order order = orderService.saveOrder(cart);
            session.removeAttribute("totalItems");
            session.removeAttribute("totalPrice");
            if (session.getAttribute("usedDiscount") != null){
                Discount discountDisable = (Discount) session.getAttribute("usedDiscount");
                discountService.disableDiscount(discountDisable);
            }
            model.addAttribute("order", order);
//            if (customer != null) {
//                emailService.sendThankYouMail(customer);
//            }
        }
        else {
            if (session.getAttribute("totalItems") == null){
                return "home";
            }
            CustomerDto customerDto = (CustomerDto) session.getAttribute("customer");
            ShoppingCart cartGuest = (ShoppingCart) session.getAttribute("shoppingCart");

            if (customerDto.getLastName() == null || customerDto.getAddress() == null|| customerDto.getPhoneNumber()==null)
            {
                System.out.println(" customer bị null");
                redirectAttributes.addFlashAttribute("error","Điền thông tin trước khi thanh toán");
                return "redirect:/check-out";
            }
            else if ( session.getAttribute("shoppingCart") == null){
                System.out.println("Cart bị rỗng");
                return "redirect:/cart";
            }
            else if (customerDto != null && cartGuest != null){
                Customer customerEx = customerService.saveCustomerGuest(customerDto);
                cartGuest.setCustomer(customerEx);
                customerEx.setCart(cartGuest);
                Order order = orderService.saveOrder(cartGuest);
                session.removeAttribute("totalItems");
                model.addAttribute("order", order);
            }
        }
        model.addAttribute("title", "Order detail");
        model.addAttribute("page", "Order detail");
        model.addAttribute("success", "Đơn hàng của bạn được thanh toán");
        return "order-detail";
    }
    @RequestMapping(value = "/cancel-order/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String cancelOrder(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        orderService.cancelOrder(id);
        redirectAttributes.addFlashAttribute("success", "Đã hủy đơn hàng");
        return "redirect:/orders";
    }

    //Hủy đơn nếu nút cancle chưa hiện
    @RequestMapping(value = "/delete-order/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteOrder(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        orderService.deleteOrder(id);
        redirectAttributes.addFlashAttribute("success", "Đã xóa đơn hàng");
        return "redirect:/orders";
    }

    //Cập nhật form checkout khách guess
    @RequestMapping(value = "/update-info-customer", params = "action=updateGuess", method = {RequestMethod.GET, RequestMethod.PUT})
    private String updateAccountGuessFormCheckout(@Valid @ModelAttribute("customer") CustomerDto customerDto,
                                             BindingResult result, RedirectAttributes redirectAttributes,
                                             HttpServletRequest request, HttpSession session){
        if(result.hasErrors()){
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors){
                redirectAttributes.addFlashAttribute("error", error.getDefaultMessage());
            }
        } else {
            try {
                customerService.updateInfoCustomerGuest(customerDto);
                redirectAttributes.addFlashAttribute("success", "Đã cập nhật thông tin");
                session.setAttribute("customer", customerDto);
            } catch (Exception e){
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error","Error from server");
            }
        }
        String referer = request.getHeader("Referer");
        if(referer !=null){
            referer = referer.replace("%20", "");
        }
        return "redirect:"+ referer;
    }

    //Cập nhật form checkout khách thành viên
    @RequestMapping(value = "/update-info-customer", params = "action=updateMember", method = {RequestMethod.GET, RequestMethod.PUT})
    private String updateAccountMemberFormCheckout(@Valid @ModelAttribute("customer") CustomerDto customerDto,
                                             BindingResult result, RedirectAttributes redirectAttributes,
                                             HttpServletRequest request, HttpSession session){
        if(result.hasErrors()){
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors){
                redirectAttributes.addFlashAttribute("error", error.getDefaultMessage());
            }
        } else {
            try {
                customerService.update(customerDto);
                redirectAttributes.addFlashAttribute("success", "Đã cập nhật thông tin");
            } catch (Exception e){
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error","Error from server");
            }
        }
        String referer = request.getHeader("Referer");
        if(referer !=null){
            referer = referer.replace("%20", "");
        }
        return "redirect:"+ referer;
    }


    @PostMapping("/discount-cart")
    public String voucherDiscount(@RequestParam("codeDiscount") String codeDiscount,
                                  Model model, Principal principal,
                                  RedirectAttributes attributes,
                                  HttpSession session){
        if (principal !=null){
            CustomerDto customerDto = customerService.getCustomerDto(principal.getName());
            ShoppingCart cart = customerService.findByUsername(principal.getName()).getCart();
            try {
                Discount discount = discountService.findByCode(codeDiscount);
                if (discount!=null){
                    cart = orderService.updateOrderByDiscount(cart, discount);
                    attributes.addFlashAttribute("success", "Đã áp mã giảm giá");
                    session.setAttribute("totalPrice", cart.getTotalPrice());
                    session.setAttribute("usedDiscount", discount);
                    model.addAttribute("customer",customerDto);
                    model.addAttribute("shoppingCart", cart);
                    model.addAttribute("title", "CheckOut");
                    model.addAttribute("page", "CheckOut");
                    return "checkout";
                }
                else {
                    attributes.addFlashAttribute("error", "Không tồn tại mã này hoặc đã sử dụng");
                }
            }catch (Exception e){
                e.printStackTrace();
                attributes.addFlashAttribute("error","Có lỗi xảy ra không thể xài voucher");
            }
        }else {
            attributes.addFlashAttribute("error","Mã voucher chỉ dành cho khách hàng có tài khoản");
        }

        return "redirect:/check-out" ;

    }








}
