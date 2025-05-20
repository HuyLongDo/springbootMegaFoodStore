package com.example.admin.controller;

import com.example.library.model.Discount;
import com.example.library.service.DiscountService;
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
public class DiscountController {

    @Autowired
    private  DiscountService discountService;

    @GetMapping("/discounts")
    public String discountPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        model.addAttribute("title","Mã khuyến mãi");
        List<Discount> discountList = discountService.findAll();
        model.addAttribute("discountList",discountList);
        model.addAttribute("size", discountList.size());
        model.addAttribute("discountNew", new Discount());
        return "discounts";
    }

    @RequestMapping(value = "/findDiscountById/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Discount findById(@PathVariable Long id){
        return discountService.findById(id);
    }

    @PostMapping("/save-discount")
    public String saveDiscount(@ModelAttribute("discountNew") Discount discount,
                       RedirectAttributes redirectAttributes) {
        try {
            discountService.save(discount);
            redirectAttributes.addFlashAttribute("success", "Add successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Trùng với mã khác, hãy điền lại thông tin");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra với dữ liệu nhập");
        }
        return "redirect:/discounts";
    }

    @GetMapping(value = "/update-discount")
    public String updateDiscount(Discount discount, RedirectAttributes redirectAttributes) {
        try {
            discountService.update(discount);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Trùng thông tin mã khác, hãy kiểm tra lại thông tin");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra với dữ liệu nhập, hãy kiểm tra lại");
        }
        return "redirect:/discounts";
    }

    @RequestMapping(value = "/delete-discount/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String deleteDiscount(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            discountService.deleteDiscount(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to delete");
        }
        return "redirect:/discounts";
    }

    @RequestMapping(value = "/enable-discount/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enableDiscount(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            discountService.enableById(id);
            attributes.addFlashAttribute("success", "Đã kích hoạt");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enabled");
        }
        return "redirect:/discounts";
    }







}
