package com.example.customer.controller;

import com.example.library.dto.ProductDto;
import com.example.library.model.CartItem;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final ShoppingCartService cartService;
    private final CustomerService customerService;
    private final ProductService productService;

//    private  static final String SESSION_KEY_GIOHANG ="shopppingCart";

    //Load giỏ hàng
    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session){
        ShoppingCart cart;
        if(principal != null){
            Customer customer = customerService.findByUsername(principal.getName());
            cart = customer.getCart();
            if(cart == null){
                model.addAttribute("check","Bạn không có bánh nào trong giỏ hàng");
                cart = new ShoppingCart();
            }
            if (cart.getTotalItems() == 0){
                model.addAttribute("check","Bạn không có bánh nào trong giỏ hàng");
            }
            session.setAttribute("totalItems", cart.getTotalItems());
            model.addAttribute("grandTotal", cart.getTotalPrice());
            model.addAttribute("shoppingCart", cart);
            model.addAttribute("cartItems", cart.getCartItems());
        } else {
             cart = (ShoppingCart) session.getAttribute("shoppingCart") ;
             if (cart == null){
                 model.addAttribute("check","Bạn không có bánh nào trong giỏ hàng(KVL)");
                 cart = new ShoppingCart();
             }
            if (cart.getTotalItems() == 0){
                model.addAttribute("check","Bạn không có bánh nào trong giỏ hàng");
            }
            session.setAttribute("totalItems", cart.getTotalItems());
            model.addAttribute("grandTotal", cart.getTotalPrice());
            model.addAttribute("shoppingCart", cart);
            model.addAttribute("cartItems", cart.getCartItems());
        }
        model.addAttribute("title","Giỏ hàng");
        model.addAttribute("page","Giỏ hàng");
        return "cart";
    }

    //Thêm sản phẩm vào giỏ
    @PostMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("id") Long productId,
                                @ModelAttribute("shoppingCart") ShoppingCart cart,
                                @RequestParam(value = "quantity",
                                         required = false, defaultValue = "1") int quantity,
                                Principal principal,
                                HttpServletRequest request,
                                Model model,
                                HttpSession session){
        if (principal != null) {
            ProductDto productDto = productService.getProductById(productId);
            String username = principal.getName();
            cart = cartService.addItemToCart(productDto, quantity, username);
            session.setAttribute("totalItems", cart.getTotalItems());
            model.addAttribute("shoppingCart", cart);
        }
        else {
            ProductDto productDto1 = productService.getProductById(productId);
            cart = (ShoppingCart) request.getSession().getAttribute("shoppingCart");

            cart = cartService.addItemToCartSession(cart, productDto1,quantity);
            session.setAttribute("totalItems", cart.getTotalItems());
            session.setAttribute("shoppingCart", cart);
        }
        String referer = request.getHeader("Referer");
        if(referer !=null){
            referer = referer.replace("%20", "");
        }
        return "redirect:"+ referer;
    }

    @PostMapping(value = "/update-cart", params = "action=update")
    public String updateCart(@RequestParam("quantity") int quantity,
                             @RequestParam("id") Long productId,
                             Model model, HttpSession session,
                             Principal principal){
        ShoppingCart cart;
        if (principal != null){
            ProductDto productDto = productService.getProductById(productId);
            String username = principal.getName();
            cart = cartService.updateCart(productDto, quantity, username);
            model.addAttribute("shoppingCart", cart);

        } else {
            ProductDto productDto = productService.getProductById(productId);
            cart = (ShoppingCart) session.getAttribute("shoppingCart");
            cart = cartService.updateCartSession(cart, productDto,quantity);
            session.setAttribute("shoppingCart", cart);
        }
        return "redirect:/cart";
    }

    @GetMapping(value = "/delete-cart-item/{id}")
    public String deleteItem(@PathVariable("id")  Long productId,
                             Model model, HttpSession session,
                             Principal principal){
        ShoppingCart cart;
        if (principal != null){
            ProductDto productDto = productService.getProductById(productId);
            String username = principal.getName();
            cart = cartService.removeItemFromCart(productDto, username);
            model.addAttribute("shoppingCart", cart);
        } else {
            ProductDto productDto = productService.getProductById(productId);
            cart = (ShoppingCart) session.getAttribute("shoppingCart");
            cart = cartService.removeItemFromCartSession(cart, productDto);
            session.setAttribute("shoppingCart", cart);
        }
        return "redirect:/cart" ;
    }




}
