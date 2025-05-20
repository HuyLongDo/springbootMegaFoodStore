package com.example.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "discounts", uniqueConstraints = @UniqueConstraint(columnNames = {"name","codeDiscount"}))
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id")
    private Long id;
    private String name;
    private String codeDiscount;
    private int valueDiscount;
    @Column(length = 5000)
    private String description;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date startDay;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDay;
    private boolean enable;

    @Override
    public String toString() {
        return "Discount{" +
                "name='" + name + '\'' +
                ", codeDiscount='" + codeDiscount + '\'' +
                ", valueDiscount=" + valueDiscount +
                ", description='" + description + '\'' +
                ", startDay=" + startDay +
                ", endDay=" + endDay +
                ", enable=" + enable +
                '}';
    }


}
