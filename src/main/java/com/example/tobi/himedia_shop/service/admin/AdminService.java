package com.example.tobi.himedia_shop.service.admin;

import com.example.tobi.himedia_shop.dto.admin.ReviewsCountResponseDTO;
import com.example.tobi.himedia_shop.dto.admin.UserIdResponseDTO;
import com.example.tobi.himedia_shop.dto.payment.*;
import com.example.tobi.himedia_shop.dto.product.product.ProductRequestDTO;
import com.example.tobi.himedia_shop.mapper.AdminMapper;
import com.example.tobi.himedia_shop.mapper.PaymentMapper;
import com.example.tobi.himedia_shop.model.Member;
import com.example.tobi.himedia_shop.model.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final FileService fileService;
    private final AdminMapper adminMapper;
    private final PaymentMapper paymentMapper;


    @Transactional
    public void productRegistration(ProductRequestDTO prDTO) {
        String path = null;
        if(!prDTO.getProductImage().isEmpty()){
            path=fileService.fileUpload(prDTO.getProductImage(),"/product/");
        }

        adminMapper.saveProduct(Products.builder()
                .title(prDTO.getProductName())
                .sellerId(prDTO.getProductUserId())
                .category(prDTO.getProductCategory())
                .content(prDTO.getProductContent())
                .contentImg(path)
                .stock(prDTO.getProductStock())
                .price(prDTO.getProductPrice())
                .temperature(prDTO.getProductTemperature())
                .precipitation(prDTO.getProductPrecipitation())
                .build()
        );
    }
    @Transactional(readOnly = true)
    public List<Member> getMembers() {
        return adminMapper.getMember();
    }

    public List<ReviewsCountResponseDTO> listProductsAndReviews() {
        return adminMapper.listProductsAndReviews();
    }


    public List<BalanceResponseDTO> getUserBalance() {
        return paymentMapper.listUserBalance();
    }

    public BalanceSumResponseDTO getRevenue() {
        BalanceSumResponseDTO result  = paymentMapper.financialSummary();
        if (result  == null) {
            result = new BalanceSumResponseDTO();
            result.setTotalDeposit(0);
            result.setTotalWithdrawal(0);
            result.setTotalWithdrawalPurchased(0);
        }
        return result;
    }

    public List<UserIdResponseDTO> getAllUserIds() {
        return adminMapper.getListUserId();
    }

    public boolean addPayment(PaymentRequestDTO paymentRequest) {
        BalanceResponseDTO balanceResponseDTO = paymentMapper.UserBalance(paymentRequest.getUserId());
        if (balanceResponseDTO == null) {
            balanceResponseDTO = new BalanceResponseDTO(); // 기본값으로 초기화
            balanceResponseDTO.setBalance(0);
        }
        if(balanceResponseDTO.getBalance()-paymentRequest.getWithdrawal()<0){
            return false;
        }
        int result = paymentMapper.addPayment(PaymentBalanceRequestDTO.builder()
                .balance(balanceResponseDTO.getBalance() - paymentRequest.getWithdrawal() + paymentRequest.getDeposit())
                .userId(paymentRequest.getUserId())
                .deposit(paymentRequest.getDeposit())
                .withdrawal(paymentRequest.getWithdrawal())
                .isPurchased(0)
                .build());

        return result > 0;
    }


    public List<SalesDataDTO> getDailySalesData() {
        return paymentMapper.findDailySalesData();
    }
}
