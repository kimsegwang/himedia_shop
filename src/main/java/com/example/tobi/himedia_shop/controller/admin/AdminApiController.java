package com.example.tobi.himedia_shop.controller.admin;


import com.example.tobi.himedia_shop.dto.admin.ReviewsCountResponseDTO;
import com.example.tobi.himedia_shop.dto.admin.UserIdResponseDTO;
import com.example.tobi.himedia_shop.dto.payment.BalanceResponseDTO;
import com.example.tobi.himedia_shop.dto.payment.BalanceSumResponseDTO;
import com.example.tobi.himedia_shop.dto.payment.PaymentRequestDTO;
import com.example.tobi.himedia_shop.dto.payment.SalesDataDTO;
import com.example.tobi.himedia_shop.dto.product.product.ProductRegistrationDTO;

import com.example.tobi.himedia_shop.dto.product.product.ProductRequestDTO;
import com.example.tobi.himedia_shop.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApiController {

    private final AdminService adminService;

    @PostMapping("/product-registration")
    public ResponseEntity<ProductRegistrationDTO> registerProduct(
            @ModelAttribute ProductRequestDTO requestDTO
    ) {
        adminService.productRegistration(requestDTO);
        return ResponseEntity.ok(
                ProductRegistrationDTO.builder()
                        .url("/admin")
                        .build()
        );
    }
    @GetMapping("/product-review-count-list")
    public List<ReviewsCountResponseDTO> ProductReviewCountList(){
        return adminService.listProductsAndReviews();
    }

    @GetMapping("/user-balance")
    public List<BalanceResponseDTO> UserBalance(){
        return adminService.getUserBalance();
    }

    @GetMapping("/revenue")
    public BalanceSumResponseDTO RevenueSum() {
        return adminService.getRevenue();
    }
    @GetMapping("/getUserIds")
    public List<UserIdResponseDTO> getUserIds() {
        return adminService.getAllUserIds(); // 모든 사용자 ID를 반환하는 서비스 메서드
    }

    @PostMapping("/payment")
    public ResponseEntity<String> addPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        if(adminService.addPayment(paymentRequest)){
            return ResponseEntity.ok("Payment added successfully");
        };
        //실패
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add payment");
    }

    @GetMapping("/sales-data")
    public ResponseEntity<List<SalesDataDTO>> getSalesData() {
        List<SalesDataDTO> salesData = adminService.getDailySalesData();
        return ResponseEntity.ok(salesData);
    }
}
