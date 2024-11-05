package com.example.tobi.himedia_shop.controller;

import com.example.tobi.himedia_shop.model.SearchProduct;
import com.example.tobi.himedia_shop.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class ProductSearchApiController {

    private final SearchService searchService;
    private final ObjectMapper objectMapper;
    @GetMapping("/order")
    public ResponseEntity<?> getSortedProducts(@RequestParam("searchKeyword") String searchKeyword,
                                               @RequestParam("sortBy") String sortBy) throws JsonProcessingException {
        // 검색 키워드가 비어있는 경우 처리
        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            return new ResponseEntity<>("검색어를 입력해주세요.", HttpStatus.BAD_REQUEST);
        }

        // 제품 검색 서비스 호출
        List<SearchProduct> products = searchService.searchProductSortBy(searchKeyword, sortBy);
        // 검색된 제품이 없는 경우 처리
        if (products == null || products.isEmpty()) {
            return new ResponseEntity<>("검색 결과가 없습니다.", HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
