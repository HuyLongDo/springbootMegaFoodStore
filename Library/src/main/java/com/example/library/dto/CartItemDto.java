package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long id;
    private ShoppingCartDto cartDto;
    private ProductDto productDto;
    private int quantity;
    private double unitPrice;

}
