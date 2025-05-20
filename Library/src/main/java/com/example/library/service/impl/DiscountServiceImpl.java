package com.example.library.service.impl;

import com.example.library.model.Discount;
import com.example.library.repository.DiscountRepository;
import com.example.library.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    @Override
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    @Override
    public Discount findById(Long id) {
        return discountRepository.findById(id).get();
    }

    @Override
    public Discount findByCode(String code) {
        return discountRepository.findByCode(code);
    }

    @Override
    public Discount save(Discount discount) {
        try {
            Discount discountNew = new Discount();
            discountNew.setName(discount.getName());
            discountNew.setDescription(discount.getDescription());
            discountNew.setCodeDiscount(discount.getCodeDiscount());
            discountNew.setValueDiscount(discount.getValueDiscount());
            discountNew.setStartDay(discount.getStartDay());
            discountNew.setEndDay(discount.getEndDay());
            discountNew.setEnable(true);
            return discountRepository.save(discountNew);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Discount update(Discount discount) {
        try {
            Discount discountNew = discountRepository.findById(discount.getId()).get();
            discountNew.setName(discount.getName());
            discountNew.setDescription(discount.getDescription());
            discountNew.setCodeDiscount(discount.getCodeDiscount());
            discountNew.setValueDiscount(discount.getValueDiscount());
            discountNew.setStartDay(discount.getStartDay());
            discountNew.setEndDay(discount.getEndDay());
            discountNew.setEnable(discount.isEnable());
            return discountRepository.save(discountNew);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteDiscount(Long id) {
        discountRepository.deleteById(id);
    }

    @Override
    public void enableById(Long id) {
        Discount discount =discountRepository.getById(id);
        if (discount.isEnable() == false){
            discount.setEnable(true);
        }else if (discount.isEnable() == true){
            discount.setEnable(false);
        }
        discountRepository.save(discount);
    }

    @Override
    public Discount disableDiscount(Discount discount) {
        Discount discountDisable = discountRepository.getById(discount.getId());
        discountDisable.setEnable(false);
        return discountRepository.save(discountDisable);
    }


}
