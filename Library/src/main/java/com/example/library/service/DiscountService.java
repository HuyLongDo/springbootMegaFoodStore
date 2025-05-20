package com.example.library.service;

import com.example.library.model.Discount;

import java.util.List;

public interface DiscountService {

    List<Discount> findAll();

    Discount findById(Long id);

    Discount findByCode(String code);

    Discount save(Discount discount);

    Discount update(Discount discount);

    void deleteDiscount(Long id);

    void enableById(Long id);

    Discount disableDiscount(Discount discount);

}
