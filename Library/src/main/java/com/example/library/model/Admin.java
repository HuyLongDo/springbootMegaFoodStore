package com.example.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admins", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "image"}))
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;
    @Size(min = 3, max = 10, message = "Nickname Cần 3-10 ký tự")
    private String firstName;
    @Size(min = 3, max = 10, message = "Tên Cần 3 tới 10 ký tự")
    private String lastName;
    private String username;
//    @Size(min = 5, max = 10, message = "Password chứa 5 tới 10 ký tự")
    private String password;
    private boolean enable;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private String image;

    //          -load role của admin ngay    -tất cả thao tác của admin
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "admins_roles",
            joinColumns = @JoinColumn(name = "admin_id", referencedColumnName = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private List<Role> roles;
}
