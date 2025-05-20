package com.example.library.repository;

import com.example.library.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);

    @Query("Select a from Admin a join a.roles r where r.name = :roleName")
    List<Admin> getUsersByRole(@Param("roleName") String roleName);

    int countBy();

}
