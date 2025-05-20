package com.example.library.service.impl;

import com.example.library.model.Order;
import com.example.library.model.OrderDetail;
import com.example.library.repository.*;
import com.example.library.service.ThongKeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThongKeServiceImpl implements ThongKeService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DiscountRepository discountRepository;
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    //Thống kê số bánh bán trong 1 tuần
    @Override
    public int countSoldCakesInWeek(){
        int totalSoldCakes = 0;
        Date dayNow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dayNow);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        Date oneWeekAgo = calendar.getTime();

        List<Order> ordersInWeek = orderRepository.findByOrderDateBetween(oneWeekAgo, dayNow);
        for (Order order : ordersInWeek){
            for (OrderDetail orderDetail : order.getOrderDetailList()){
                totalSoldCakes = totalSoldCakes + orderDetail.getQuantity();
            }
        }
        return totalSoldCakes;
    }

    //Thống kê số đơn hàng trong 1 tuần
    @Override
    public int countOrdersSoldInWeek(){
        int totalOrders = 0;
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        Date oneWeek = calendar.getTime();

        List<Order> ordersInWeek = orderRepository.findByOrderDateBetween(oneWeek, currentDate);
        for (Order order : ordersInWeek){
            totalOrders++;
        }
        return totalOrders;
    }

    //Thống kê doanh thu 1 tuần
    @Override
    public double countTotalPriceAllOrder(){
        double totalPrice = 0;
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        Date oneWeek = calendar.getTime();

        List<Order> ordersInWeek = orderRepository.findByOrderDateBetween(oneWeek, currentDate);
        for (Order order : ordersInWeek){
            totalPrice = totalPrice + order.getTotalPrice();
        }
        return totalPrice;
    }

    @Override
    public int countAllProduct() {
        return productRepository.countBy();
    }

    @Override
    public int countAllCategory() {
        return categoryRepository.countBy();
    }

    @Override
    public int countVoucher() {
        return discountRepository.countBy();
    }

    @Override
    public int countEmployee() {
        return adminRepository.countBy();
    }

    @Override
    public int countImageProduct() {
        return productRepository.countBy();
    }

    @Override
    public int countCustomer() {
        return customerRepository.countBy();
    }

    @Override
    public int countSoldOrder() {
        return orderRepository.countSoldOrders();
    }

    @Override
    public int countCancelOrder() {
        return orderRepository.countCancelOrders();
    }

    @Override
    public double countTotalPriceTwoWeekAgo() {
        double totalPrice =0;
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        Date oneWeekAgo = calendar.getTime();

        // Lấy 2 tuần trước
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        Date twoWeeksAgo = calendar.getTime();
        List<Order> ordersInWeek = orderRepository.findByOrderDateBetween(twoWeeksAgo, oneWeekAgo);

        for (Order order : ordersInWeek){
            totalPrice = totalPrice + order.getTotalPrice();
        }

        return totalPrice;
    }




}
