package com.example.tobi.himedia_shop.controller;

import com.example.tobi.himedia_shop.model.SearchProduct;
import com.example.tobi.himedia_shop.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final SearchService searchService;

    @GetMapping("/category/{category}/{subCategory}")
    public String categoryDetail(
            @PathVariable("category") String category,
            @PathVariable("subCategory") String subCategory,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        // If keyword is present, redirect to the search page
        if (keyword != null && !keyword.isEmpty()) {
            return "redirect:/search?keyword=" + keyword;
        }


        List<SearchProduct> products = searchService.categoryProduct(category,subCategory,"newest");

        // 하부 카테고리 이름 가져오기
        //모델에 하부 카테고리 이름 넣기
        model.addAttribute("subCategoryName", subCategory);
        model.addAttribute("CategoryName", category);
        model.addAttribute("products", products);
        model.addAttribute("SearchKeyword", keyword);


        return "category-detail";
    }


}



