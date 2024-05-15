package com.example.library.repository;

import com.example.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /*admin*/
//    @Query("select p from  Product p")
//    Page<Product> getAllPageProducts(Pageable pageable);
//
//    @Query("select p from Product p where p.description like %?1% or p.name like %?1% ")
//    Page<Product> searchProducts(String keyword, Pageable pageable);

    @Query("select p from Product p where p.description like %?1% or p.name like %?1%")
    List<Product> searchProductsList(String keyword);

/*    Customer*/

    @Query("select p from Product p where p.is_deleted = false and p.is_activated = true")
    List<Product> getAllProducts();

    @Query(value = "select * from products p where p.is_deleted = false and p.is_activated = true order by RAND() asc limit 4 ", nativeQuery = true)
    List<Product> listViewProduct();

    @Query(value = "select p from Product p inner join Category c on c.id = ?1 and p.category.id = ?1 " +
            "where p.is_activated = true and p.is_deleted = false")
    List<Product> findProductByCategoryId(Long id);

    @Query(value ="select p from Product p inner join Category c ON c.id = p.category.id" +
            " where p.category.name = ?1 and p.is_activated = true and p.is_deleted = false")
    List<Product> findAllByCategory(String category);

    @Query(value = "select " +
            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted " +
            "from products p where p.is_deleted = false and p.is_activated = true " +
            "order by p.cost_price desc limit 9", nativeQuery = true)
    List<Product> filterHighProducts();

    @Query(value = "select " +
            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted " +
            "from products p where p.is_deleted = false and p.is_activated = true " +
            "order by p.cost_price asc limit 9", nativeQuery = true)
    List<Product> filterLowerProducts();

}
