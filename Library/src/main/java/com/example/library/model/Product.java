package com.example.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "image"}))
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;
    @Column(length = 5000)
    private String description;
    @Column(precision = 6)
    private int currentQuantity;
    private double costPrice;
    private double salePrice;
    private boolean is_activated;
    private boolean is_deleted;
//    @Column(length = 5000)
//    private String Structure;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private String image;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;




}
