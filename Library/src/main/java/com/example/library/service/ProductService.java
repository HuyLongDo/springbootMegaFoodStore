package com.example.library.service;

import com.example.library.dto.ProductDto;
import com.example.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    //Admin
    Product save(MultipartFile imageProduct, ProductDto productDto);

    Product update(MultipartFile imageProduct, ProductDto productDto);

    void deleteProduct(Long id);

    void enableById(Long id);

    void disableById(Long id);

    List<ProductDto> getListProductDto();

    ProductDto getProductById(Long id);

    Page<ProductDto> searchProducts(int pageNo, String keyword);

    Page<ProductDto> pageProduct(int pageNo);

    //Customer
    List<ProductDto> getAllProductCustomer();

    List<ProductDto> listViewProducts();

    List<ProductDto> findByCategoryId(Long id);
    
    List<ProductDto> findAllByCategory(String category);

    List<ProductDto> filterHighProducts();

    List<ProductDto> filterLowerProducts();

//    Product findById(Long id);
//
//    List<ProductDto> randomProduct();

//    Page<ProductDto> getAllProductsForCustomer(int pageNo);

    List<ProductDto> searchProducts(String keyword);
}
