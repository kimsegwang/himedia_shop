package com.example.tobi.himedia_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CategoryController {

    @GetMapping("/category/{category}/{subCategory}")
    public String categoryDetail(
            @PathVariable("category") String category,
            @PathVariable("subCategory") String subCategory,
            Model model) {
        // 하부 카테고리 이름 가져오기
        //모델에 하부 카테고리 이름 넣기
        model.addAttribute("subCategoryName", subCategory);
        model.addAttribute("CategoryName", category);


        return "category-detail";
    }
}



