package com.example.admin.controller;

import com.example.library.dto.ProductDto;
import com.example.library.model.Category;
import com.example.library.service.CategoryService;
import com.example.library.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    //Load trang product
    @GetMapping("/products")
    public String products(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        List<ProductDto> productDtos = productService.getListProductDto();
        model.addAttribute("productDtos", productDtos);
        model.addAttribute("size", productDtos.size());
        return "products";
    }

    @GetMapping("/products/{pageNo}")
    public String paginationProductPage(@PathVariable("pageNo") int pageNo,
                                        Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        Page<ProductDto> productDtoPage = productService.pageProduct(pageNo);
        model.addAttribute("title", "Page products");
        model.addAttribute("size", productDtoPage.getSize());
        model.addAttribute("productDtos", productDtoPage);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", productDtoPage.getTotalPages());
        return "products";
    }

    @GetMapping("/search-result/{pageNo}")
    public String searchProducts(@PathVariable("pageNo") int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        Page<ProductDto> productDtoPage = productService.searchProducts(pageNo, keyword);
        model.addAttribute("title", "Search Result");
        model.addAttribute("productDtos", productDtoPage);
        model.addAttribute("size", productDtoPage.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", productDtoPage.getTotalPages());
        return "result-product";
    }

    //Load trang add product khi nhấn nút
    @GetMapping("/add-product")
    public String addProductPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Add Product");
        List<Category> categories = categoryService.findAllByActivatedTrue();
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", new ProductDto());
        return "add-product";
    }

    //Save product khi thêm mới info product
    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("productDto") ProductDto productDto,
                              @RequestParam("imageProduct") MultipartFile imageProduct,
                              RedirectAttributes redirectAttributes) {
        try {
            productService.save(imageProduct, productDto);
            redirectAttributes.addFlashAttribute("success", "Đã thêm bánh mới.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to add new product!");
        }
        return "redirect:/products/0";
    }

    //Load trang update product khi bấm update
    @GetMapping("/update-product-page/{id}")
    public String updateProductPage(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Update Product");
        List<Category> categories = categoryService.findAllByActivatedTrue();
        ProductDto productDto = productService.getProductById(id);
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);
        return "update-product";
    }

    //Save lại product sau khi update info mới
    @PostMapping("/update-product/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct") MultipartFile imageProduct,
                                RedirectAttributes redirectAttributes) {
        try {
            productService.update(imageProduct, productDto);
            redirectAttributes.addFlashAttribute("success", "Đã cập nhật thông tin bánh.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server, please try again!");
        }
        return "redirect:/products/0";
    }

    @RequestMapping(value = "/delete-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("success", "Đã xóa bánh.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error, can't delete");
        }
        return "redirect:/products/0";
    }

    @RequestMapping(value = "/enable-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enabledProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Đã kích hoạt bán.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Enabled failed!");
        }
        return "redirect:/products/0";
    }

    @RequestMapping(value = "/disable-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String disableProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.disableById(id);
            redirectAttributes.addFlashAttribute("success", "Đã ngừng bán");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Disabled failed!");
        }
        return "redirect:/products/0";
    }


}
