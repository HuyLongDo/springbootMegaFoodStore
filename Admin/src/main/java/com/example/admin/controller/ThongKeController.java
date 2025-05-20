package com.example.admin.controller;

import com.example.library.dto.CategoryDto;
import com.example.library.service.CategoryService;
import com.example.library.service.ThongKeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ThongKeController {

    private final ThongKeService thongKeService;
    private final CategoryService categoryService;

    @GetMapping("/thongke-page")
    public String orderPage(Model model, Principal principal, HttpSession session){
        if (principal == null) {
            return "redirect:/login";
        }

        int totalProductStore = thongKeService.countAllProduct();
        int totalCategoryStore = thongKeService.countAllCategory();
        int totalVoucherStore = thongKeService.countVoucher();
        int totalEmployeeStore = thongKeService.countEmployee();
        int totalImageStore = thongKeService.countImageProduct();
        int totalCustomer = thongKeService.countCustomer();
        List<CategoryDto> categories = categoryService.getCategoriesAndSize();
        int totalAllSoldOrder = thongKeService.countSoldOrder();
        int totalCancelOrder = thongKeService.countCancelOrder();

        model.addAttribute("totalProductStore",totalProductStore);
        model.addAttribute("totalCategoryStore",totalCategoryStore);
        model.addAttribute("totalVoucherStore",totalVoucherStore);
        model.addAttribute("totalEmployeeStore",totalEmployeeStore);
        model.addAttribute("totalImageStore",totalImageStore);
        model.addAttribute("totalCustomer",totalCustomer);
        model.addAttribute("categories",categories);
        model.addAttribute("totalAllSoldOrder",totalAllSoldOrder);
        model.addAttribute("totalCancelOrder",totalCancelOrder);
        model.addAttribute("title","Trang thống kê");

        int totalSoldCakes = thongKeService.countSoldCakesInWeek();
        model.addAttribute("totalSoldCakes", totalSoldCakes);

        double totalSoldOrder = thongKeService.countOrdersSoldInWeek();
        model.addAttribute("totalSoldOrder", totalSoldOrder);

        double totalPriceOrder = thongKeService.countTotalPriceAllOrder();
        model.addAttribute("totalPriceOrder", totalPriceOrder);

        return "thongke";
    }


    @GetMapping("/doanh-thu-OneWeekAgo")
    public String thongKeDoanhThuOneWeekAgo(Model model, Principal principal, HttpSession session){
        if (principal == null) {
            return "redirect:/login";
        }else {

            model.addAttribute("title","Thống kê đơn tuần trước");

            int totalProductStore = thongKeService.countAllProduct();
            int totalCategoryStore = thongKeService.countAllCategory();
            int totalVoucherStore = thongKeService.countVoucher();
            int totalEmployeeStore = thongKeService.countEmployee();
            int totalImageStore = thongKeService.countImageProduct();
            int totalCustomer = thongKeService.countCustomer();
            List<CategoryDto> categories = categoryService.getCategoriesAndSize();
            int totalAllSoldOrder = thongKeService.countSoldOrder();
            int totalCancelOrder = thongKeService.countCancelOrder();

            model.addAttribute("totalProductStore",totalProductStore);
            model.addAttribute("totalCategoryStore",totalCategoryStore);
            model.addAttribute("totalVoucherStore",totalVoucherStore);
            model.addAttribute("totalEmployeeStore",totalEmployeeStore);
            model.addAttribute("totalImageStore",totalImageStore);
            model.addAttribute("totalCustomer",totalCustomer);
            model.addAttribute("categories",categories);
            model.addAttribute("totalAllSoldOrder",totalAllSoldOrder);
            model.addAttribute("totalCancelOrder",totalCancelOrder);
            model.addAttribute("title","Trang thống kê");

            int totalSoldCakes = thongKeService.countSoldCakesInWeek();
            model.addAttribute("totalSoldCakes", totalSoldCakes);

            double totalSoldOrder = thongKeService.countOrdersSoldInWeek();
            model.addAttribute("totalSoldOrder", totalSoldOrder);

            double totalPriceOrderOneWeek = thongKeService.countTotalPriceAllOrder();
            model.addAttribute("totalPriceOrder", totalPriceOrderOneWeek);
            model.addAttribute("totalPriceOrderOneWeek", totalPriceOrderOneWeek);

            return "thongke";
        }
    }

    @GetMapping("/doanh-thu-TwoWeekAgo")
    public String thongKeDoanhThuTwoWeekAgo(Model model, Principal principal, HttpSession session){
        if (principal == null) {
            return "redirect:/login";
        }else {
            double totalPriceTwoWeek = thongKeService.countTotalPriceTwoWeekAgo();
            model.addAttribute("title","Thống kê số bánh");
            model.addAttribute("totalPriceTwoWeek", totalPriceTwoWeek);
            session.setAttribute("totalPriceTwoWeek", totalPriceTwoWeek);

            int totalProductStore = thongKeService.countAllProduct();
            int totalCategoryStore = thongKeService.countAllCategory();
            int totalVoucherStore = thongKeService.countVoucher();
            int totalEmployeeStore = thongKeService.countEmployee();
            int totalImageStore = thongKeService.countImageProduct();
            int totalCustomer = thongKeService.countCustomer();
            List<CategoryDto> categories = categoryService.getCategoriesAndSize();
            int totalAllSoldOrder = thongKeService.countSoldOrder();
            int totalCancelOrder = thongKeService.countCancelOrder();

            model.addAttribute("totalProductStore",totalProductStore);
            model.addAttribute("totalCategoryStore",totalCategoryStore);
            model.addAttribute("totalVoucherStore",totalVoucherStore);
            model.addAttribute("totalEmployeeStore",totalEmployeeStore);
            model.addAttribute("totalImageStore",totalImageStore);
            model.addAttribute("totalCustomer",totalCustomer);
            model.addAttribute("categories",categories);
            model.addAttribute("totalAllSoldOrder",totalAllSoldOrder);
            model.addAttribute("totalCancelOrder",totalCancelOrder);
            model.addAttribute("title","Trang thống kê");

            int totalSoldCakes = thongKeService.countSoldCakesInWeek();
            model.addAttribute("totalSoldCakes", totalSoldCakes);

            double totalSoldOrder = thongKeService.countOrdersSoldInWeek();
            model.addAttribute("totalSoldOrder", totalSoldOrder);

            double totalPriceOrder = thongKeService.countTotalPriceAllOrder();
            model.addAttribute("totalPriceOrder", totalPriceOrder);
            model.addAttribute("totalPriceOrderOneWeek", totalPriceOrder);

            return "thongke";
        }
    }





}
