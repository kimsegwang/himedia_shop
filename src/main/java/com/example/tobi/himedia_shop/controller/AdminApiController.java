package com.example.tobi.himedia_shop.controller;


import com.example.tobi.himedia_shop.dto.ProductRegistrationDTO;

import com.example.tobi.himedia_shop.dto.ProductRequestDTO;
import com.example.tobi.himedia_shop.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApiController {

    private final AdminService adminService;

    @PostMapping("ProductRegistration")
    public ResponseEntity<ProductRegistrationDTO> ProductRegistration(
            @ModelAttribute ProductRequestDTO requestDTO
    ) {
        adminService.productRegistration(requestDTO);
        return ResponseEntity.ok(
                ProductRegistrationDTO.builder()
                        .url("/admin")
                        .build()
        );
    }

}
