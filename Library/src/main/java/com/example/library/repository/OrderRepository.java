package com.example.library.repository;

import com.example.library.model.Order;
import com.example.library.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderDateBetween(Date startDate, Date endDay);

//    @Query("select count(o) from Order o where o.deliveryDate BETWEEN :startDate and :endDate")
//    int countOrdersInWeek(Date startDate, Date endDate);

    @Query("select o from Order o where o.orderStatus like 'Đã hủy' ")
    List<Order> getAllOrderByCancel();

    @Query("select o from Order o where o.orderStatus like 'Đang chờ xác nhận' ")
    List<Order> getAllOrderByWaiting();

    @Query("select o from Order o where o.orderStatus like 'Đã xác nhận' ")
    List<Order> getAllOrderByAccept();

    @Query("select count(o) from Order o where o.orderStatus like 'Đã xác nhận' ")
    int countSoldOrders();

    @Query("select count(o) from Order o where o.orderStatus like 'Đã hủy' ")
    int countCancelOrders();



}
