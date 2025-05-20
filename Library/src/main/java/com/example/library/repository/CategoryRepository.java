package com.example.library.repository;

import com.example.library.dto.CategoryDto;
import com.example.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    int countBy();

    @Query(value = "select c from Category c where c.activated = true and c.deleted = false")
    List<Category> findAllByActivated();

//    /*Customer*/
    @Query("select new com.example.library.dto.CategoryDto(c.id, c.name, count(p.category.id))" +
            " from Category c left join Product p on p.category.id = c.id " +
            " where c.activated = true and c.deleted = false group by c.id")
    List<CategoryDto> getCategoryAndSize();

}
