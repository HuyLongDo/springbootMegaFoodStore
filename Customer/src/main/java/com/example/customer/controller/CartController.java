package com.example.customer.controller;

import com.example.library.dto.ProductDto;
import com.example.library.model.Customer;
import com.example.library.model.ShoppingCart;
import com.example.library.service.CustomerService;
import com.example.library.service.ProductService;
import com.example.library.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final ShoppingCartService cartService;
    private final CustomerService customerService;
    private  final ProductService productService;

    //Load giỏ hàng
    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session){
        if(principal == null){
            return "redirect:/login";
        }
        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart cart = customer.getCart();
        if(cart.getCartItems().isEmpty()){
            model.addAttribute("check","You don't have any items in your cart");
            cart = new ShoppingCart();
        }
        session.setAttribute("totalItems", cart.getTotalItems());
        model.addAttribute("grandTotal", cart.getTotalPrice());
        model.addAttribute("shoppingCart", cart);
        model.addAttribute("cartItems", cart.getCartItems());

        model.addAttribute("title","Cart");
        model.addAttribute("page","Cart");
        return "cart";
    }

    //Thêm sản phẩm vào giỏ
    @PostMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("id") Long productId,
                                @RequestParam(value = "quantity",
                                        required = false, defaultValue = "1") int quantity,
                                Principal principal,
                                HttpServletRequest request,
                                Model model,
                                HttpSession session){

        if (principal == null) {
            return "redirect:/login";
        }
            ProductDto productDto = productService.getProductById(productId);
            String username = principal.getName();
            ShoppingCart cart = cartService.addItemToCart(productDto, quantity, username);
            session.setAttribute("totalItems", cart.getTotalItems());
            model.addAttribute("shoppingCart", cart);

            String referer = request.getHeader("Referer");
            if(referer !=null){
                referer = referer.replace("%20", "");
            }
        return "redirect:"+ referer;
    }

    @PostMapping(value = "/update-cart", params = "action=update")
    public String updateCart(@RequestParam("quantity") int quantity,
                             @RequestParam("id") Long productId,
                             Model model,
                             Principal principal){
        if (principal == null){
            return "redirect:/login";
        } else {
            ProductDto productDto = productService.getProductById(productId);
            String username = principal.getName();
            ShoppingCart cart = cartService.updateCart(productDto, quantity, username);
            model.addAttribute("shoppingCart", cart);
            return "redirect:/cart";
        }
    }

    @PostMapping(value = "/update-cart" , params = "action=delete")
    public String deleteItem(@RequestParam("id") Long productId,
                             Model model,
                             Principal principal){
        if (principal == null){
            return "redirect:/login";
        } else {
            ProductDto productDto = productService.getProductById(productId);
            String username = principal.getName();
            ShoppingCart cart = cartService.removeItemFromCart(productDto, username);
            model.addAttribute("shoppingCart", cart);
            return "redirect:/cart" ;
        }
    }





}
