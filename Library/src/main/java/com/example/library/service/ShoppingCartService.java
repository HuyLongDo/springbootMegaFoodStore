package com.example.library.service;

import com.example.library.dto.ProductDto;
import com.example.library.dto.ShoppingCartDto;
import com.example.library.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username);

    ShoppingCart updateCart(ProductDto productDto, int quantity, String username);

    ShoppingCart removeItemFromCart(ProductDto productDto, String username);

    ShoppingCart addItemToCartSession(ShoppingCart cart, ProductDto productDto, int quantity);

    ShoppingCart updateCartSession(ShoppingCart cart, ProductDto productDto, int quantity);

    ShoppingCart removeItemFromCartSession(ShoppingCart cart, ProductDto productDto);

    //    ShoppingCart combineCart(ShoppingCartDto cartDto, ShoppingCart cart);

    void deleteCartById(Long id);

    ShoppingCart getCart(String username);

    ShoppingCart getCartByIdCustomer(Long id);

//    List<ShoppingCart> getListCart(String username);


}
