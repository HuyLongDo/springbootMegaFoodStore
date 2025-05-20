package com.example.library.service.impl;

import com.example.library.model.*;
import com.example.library.repository.CustomerRepository;
import com.example.library.repository.OrderDetailRepository;
import com.example.library.repository.OrderRepository;
import com.example.library.service.OrderService;
import com.example.library.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ShoppingCartService cartService;
    private final CustomerRepository customerRepository;

    @Override
    public Order saveOrder(ShoppingCart shoppingCart) {
        try {
            Order order = new Order();
            order.setCustomer(shoppingCart.getCustomer());
            order.setOrderDate(new Date());
            order.setTotalPrice(shoppingCart.getTotalPrice());
            order.setAccept(false);
            order.setPaymentMethod("Tiền mặt");
            order.setOrderStatus("Đang chờ xác nhận");
            placeDayOrder(order);
            order.setQuantity(shoppingCart.getTotalItems());
            List<OrderDetail> orderDetailList = new ArrayList<>();
            for (CartItem item : shoppingCart.getCartItems()){
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(item.getProduct());
                orderDetail.setQuantity(item.getQuantity());
                orderDetail.setUnitPrice(item.getUnitPrice());
                orderDetailRepository.save(orderDetail);
                orderDetailList.add(orderDetail);
            }
            order.setOrderDetailList(orderDetailList);
            cartService.deleteCartById(shoppingCart.getId());
            return orderRepository.save(order);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ShoppingCart updateOrderByDiscount(ShoppingCart cart, Discount discount) {
        cart.setTotalPrice(cart.getTotalPrice() - (cart.getTotalPrice() * (discount.getValueDiscount()/100.0)));
        return cart;
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
    public List<Order> getAllOrderByCancel() {
        return orderRepository.getAllOrderByCancel();
    }

    @Override
    public List<Order> getAllOrderByAccept() {
        return orderRepository.getAllOrderByAccept();
    }

    @Override
    public List<Order> getAllOrderByWaiting() {
        return orderRepository.getAllOrderByWaiting();
    }



    @Override
    public Order acceptOrder(Order order, double shippingFee) {
        Order orderSave = orderRepository.findById(order.getId()).get();
        orderSave.setAccept(true);
        placeDayOrder(orderSave);
        orderSave.setOrderStatus("Đã xác nhận");
        orderSave.setShippingFee(shippingFee);
        orderSave.setNotes(order.getNotes());
        return orderRepository.save(orderSave);
    }

    @Override
    public Order cancelOrder(Long id) {
        Order orderCancel = orderRepository.findById(id).get();
        orderCancel.setAccept(false);
        orderCancel.setOrderStatus("Đã hủy");
        return orderRepository.save(orderCancel);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }


    //Dự kiến giao sau 7 ngày
    public void placeDayOrder(Order order){
        Date currentDate = new Date();

        //Thiết lập ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        //Thêm 7 ngày vào ngày hiện tại
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        Date deliveryDate = calendar.getTime();

        // Thiết lập ngày dự kiến giao cho đơn hàng
        order.setDeliveryDate(deliveryDate);
    }



}
