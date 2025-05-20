package com.example.library.repository;

import com.example.library.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query("select d from Discount d where d.codeDiscount = :codeDiscount")
    Discount findByCode(@Param("codeDiscount") String codeDiscount);

    int countBy();

}
