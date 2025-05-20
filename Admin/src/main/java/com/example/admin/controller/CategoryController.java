package com.example.admin.controller;

import com.example.library.model.Category;
import com.example.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Category");
        List<Category> categories = categoryService.findALl();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("categoryNew", new Category());
        return "categories";
    }

    @RequestMapping(value = "/findById/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Category findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PostMapping("/save-category")
    public String save(@ModelAttribute("categoryNew") Category category,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        try {
            if (category.getName() == null || category.getName().isEmpty()) {
                model.addAttribute("categoryNew", category);
                model.addAttribute("error", "Rỗng tên loại!");
                return "categories";
            } else {
                categoryService.save(category);
                model.addAttribute("categoryNew", category);
                redirectAttributes.addFlashAttribute("success", "Đã thêm loại mới");
            }
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Trùng tên bánh, hãy kiểm tra lại");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/categories";
    }

    @GetMapping(value = "/update-category")
    public String update(Category category, RedirectAttributes redirectAttributes) {
        try {
            categoryService.update(category);
            redirectAttributes.addFlashAttribute("success", "Đã cập nhật loại bánh.");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Trùng tên bánh, hãy kiểm tra lại");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error from server or duplicate name of category, please check again!");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/deleteCategory/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("success", "Đã xóa loại bánh");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Không thể xóa");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/enable-category/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            categoryService.enableById(id);
            attributes.addFlashAttribute("success", "Đã kích hoạt");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enabled");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/disable-category/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.disableById(id);
            redirectAttributes.addFlashAttribute("success", "Đã vô hiệu.");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to disable");
        }
        return "redirect:/categories";
    }


}
