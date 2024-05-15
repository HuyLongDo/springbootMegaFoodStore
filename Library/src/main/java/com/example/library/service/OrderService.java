package com.example.library.service;

import com.example.library.model.Order;
import com.example.library.model.ShoppingCart;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {

    Order saveOrder(ShoppingCart shoppingCart);

    List<Order> getOrderByUser(String username);

    Order getOrderById(Long id);

    List<Order> getAllOrder();

    Order acceptOrder(Order order);

    void cancelOrder(Long id);

}
