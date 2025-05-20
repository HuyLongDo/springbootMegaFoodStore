package com.example.library.service;

public interface ThongKeService {

    //revenue
    int countSoldCakesInWeek();

    int countOrdersSoldInWeek();

    double countTotalPriceAllOrder();

    double countTotalPriceTwoWeekAgo();

    //shop
    int countAllProduct();

    int countAllCategory();

    int countVoucher();

    int countEmployee();

    int countImageProduct();

    int countCustomer();

    int countSoldOrder();

    int countCancelOrder();

}
