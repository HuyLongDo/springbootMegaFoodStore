package com.example.customer.controller;

import com.example.library.dto.CategoryDto;
import com.example.library.dto.ProductDto;
import com.example.library.model.Category;
import com.example.library.service.CategoryService;
import com.example.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    //Trả về trang index
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("page","Menu");
        model.addAttribute("title","Menu");
        List<Category> categories = categoryService.findAllByActivatedTrue();
        List<ProductDto> productDtos = productService.getListProductDto();
        model.addAttribute("productDto", productDtos);
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/shop-detail")
    public String shopDetail(Model model){
        List<ProductDto> productDtos = productService.getAllProductCustomer();
        List<ProductDto> listViewProduct = productService.listViewProducts();
        List<CategoryDto> categoryDtos = categoryService.getCategoriesAndSize();
        model.addAttribute("page","Cửa Hàng");
        model.addAttribute("title","Cửa Hàng");
        model.addAttribute("products", productDtos);
        model.addAttribute("productViews", listViewProduct);
        model.addAttribute("categories", categoryDtos);
        return "shop";
    }

    //Chi tiết product
    @GetMapping("/product-detail/{id}")
    public String ProductDetails(@PathVariable("id") Long id, Model model) {
        ProductDto product = productService.getProductById(id);
        List<ProductDto> productList = productService.findAllByCategory(product.getCategory().getName());
        model.addAttribute("products", productList);
        model.addAttribute("title", "Chi Tiết Sản Phẩm");
        model.addAttribute("page", "Chi Tiết Sản Phẩm");
        model.addAttribute("productDetail", product);
        return "product-detail";
    }

    //Lấy tất cả product của mỗi category đó
    @GetMapping("/product-in-category/{id}")
    public String productsInCategoryShop(@PathVariable("id") Long id, Model model) {
        List<CategoryDto> categoryDtos = categoryService.getCategoriesAndSize();
        List<ProductDto> productDtos = productService.findByCategoryId(id);
        List<ProductDto> listView = productService.listViewProducts();
        Category categoryName = categoryService.findById(id);
        model.addAttribute("productViews", listView);
        model.addAttribute("categories", categoryDtos);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("products", productDtos);
        model.addAttribute("title", productDtos.get(0).getCategory().getName());
        model.addAttribute("page", "Danh mục bánh");
        return "products-in-category";
    }

    @GetMapping("/high-price")
    public String filterHighPrice(Model model){
        List<CategoryDto> categories = categoryService.getCategoriesAndSize();
        List<ProductDto> products = productService.filterHighProducts();
        List<ProductDto> listView = productService.listViewProducts();
        model.addAttribute("categories", categories);
        model.addAttribute("productViews", listView);
        model.addAttribute("products", products);
        model.addAttribute("title","Menu");
        model.addAttribute("page", "Menu");
        return "shop";
    }

    @GetMapping("/lower-price")
    public String filterLowerPrice(Model model){
        List<CategoryDto> categories = categoryService.getCategoriesAndSize();
        List<ProductDto> products = productService.filterLowerProducts();
        List<ProductDto> listView = productService.listViewProducts();
        model.addAttribute("categories", categories);
        model.addAttribute("productViews", listView);
        model.addAttribute("products", products);
        model.addAttribute("title","Menu");
        model.addAttribute("page", "Menu");
        return "shop";
    }

    @GetMapping("/search-product")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model){
        List<CategoryDto> categoryDtoList = categoryService.getCategoriesAndSize();
        List<ProductDto> productDtoList = productService.searchProducts(keyword);
        List<ProductDto> listView = productService.listViewProducts();
        model.addAttribute("productViews",listView);
        model.addAttribute("categories",categoryDtoList);
        model.addAttribute("products",productDtoList);
        model.addAttribute("title","Kết quả tìm");
        model.addAttribute("page","Kết quả tìm");
        return "shop";
    }




}
