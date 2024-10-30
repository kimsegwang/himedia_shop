package com.example.tobi.himedia_shop.controller.admin;


import com.example.tobi.himedia_shop.dto.product.product.ProductRegistrationDTO;

import com.example.tobi.himedia_shop.dto.product.product.ProductRequestDTO;
import com.example.tobi.himedia_shop.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
