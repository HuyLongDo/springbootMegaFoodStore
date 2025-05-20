package com.example.library.service;

import com.example.library.dto.CategoryDto;
import com.example.library.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findALl();

    Category findById(Long id);

    Category save(Category category);

    Category update(Category category);

    void disableById(Long id);

    void enableById(Long id);

    void deleteCategory(Long id);

    List<Category> findAllByActivatedTrue();

//    Optional<Category> findById(Long id);

    //Customer

    List<CategoryDto> getCategoriesAndSize();
}
