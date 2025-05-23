package com.example.library.dto;

import com.example.library.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDto {

    private Long id;
    private Customer customer;
    private double totalPrice;
    private int totalItems;
    private Set<CartItemDto> cartItemDtos;

}
