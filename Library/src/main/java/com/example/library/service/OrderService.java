package com.example.library.service;

import com.example.library.model.Discount;
import com.example.library.model.Order;
import com.example.library.model.OrderDetail;
import com.example.library.model.ShoppingCart;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {

    Order saveOrder(ShoppingCart shoppingCart);

    ShoppingCart updateOrderByDiscount(ShoppingCart cart, Discount discount);

    List<Order> getOrderByUser(String username);

    Order getOrderById(Long id);

    List<Order> getAllOrder();

    List<Order> getAllOrderByCancel();

    List<Order> getAllOrderByAccept();

    List<Order> getAllOrderByWaiting();

    Order acceptOrder(Order order, double shippingFee);

    Order cancelOrder(Long id);

    void deleteOrder(Long id);




}
