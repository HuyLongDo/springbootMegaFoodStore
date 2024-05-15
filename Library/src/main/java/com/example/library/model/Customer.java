package com.example.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(columnNames = {"username","phone_number"}))
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;
    @Size(min = 3, max = 10, message = "First name contains 3-10 characters")
    private String firstName;
    @Size(min = 3, max = 10, message = "Last name contains 3-10 characters")
    private String lastName;
    private String username;
    private String password;
    @Column(name = "phone_number")
    @Size(min = 10, max = 15, message = "Phone number contains 10-15 characters")
    private String phoneNumber;
    private String address;

    @Column(name = "cityName")
    private String cityName;

//    private String country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    private City city;

    @OneToOne(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private ShoppingCart cart;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "customer_role",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName ="customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;

}
