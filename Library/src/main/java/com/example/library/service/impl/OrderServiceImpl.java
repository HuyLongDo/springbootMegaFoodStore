package com.example.library.service.impl;

import com.example.library.model.*;
import com.example.library.repository.CustomerRepository;
import com.example.library.repository.OrderDetailRepository;
import com.example.library.repository.OrderRepository;
import com.example.library.service.OrderService;
import com.example.library.service.ShoppingCartService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ShoppingCartService cartService;
    private final CustomerRepository customerRepository;

    @Override
    public Order saveOrder(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setCustomer(shoppingCart.getCustomer());
        order.setOrderDate(new Date());
        order.setTotalPrice(shoppingCart.getTotalPrice());
        order.setAccept(false);
        order.setPaymentMethod("Cash");
        order.setOrderStatus("Đang xử lý..");
        placeDayOrder(order);
        order.setQuantity(shoppingCart.getTotalItems());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (CartItem item : shoppingCart.getCartItems()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(item.getProduct());
            orderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
        }
        order.setOrderDetailList(orderDetailList);
        cartService.deleteCartById(shoppingCart.getId());
        return orderRepository.save(order);

    }

    @Override
    public List<Order> getOrderByUser(String username) {
        Customer customer = customerRepository.findByUsername(username);
        List<Order> orderList = customer.getOrders();
        return orderList;
    }

    @Override
    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id).get();
        return order;
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Order acceptOrder(Order order) {
        Order orderSave = orderRepository.findById(order.getId()).get();
        orderSave.setAccept(true);
        orderSave.setDeliveryDate(order.getDeliveryDate());
        orderSave.setOrderStatus(order.getOrderStatus());
        orderSave.setShippingFee(order.getShippingFee());
        return orderRepository.save(orderSave);
    }

    @Override
    public void cancelOrder(Long id) {
        orderRepository.deleteById(id);
    }

    //Dự kiến giao sau 7 ngày
    public void placeDayOrder(Order order){
        Date currentDate = new Date();

        //Thiết lập ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        //Thêm 7 ngày vào ngày hiện tại
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date deliveryDate = calendar.getTime();

        // Thiết lập ngày dự kiến giao cho đơn hàng
        order.setDeliveryDate(deliveryDate);
    }


}
