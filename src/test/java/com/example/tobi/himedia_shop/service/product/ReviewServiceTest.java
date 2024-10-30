package com.example.tobi.himedia_shop.service.product;

import com.example.tobi.himedia_shop.dto.product.review.ReviewRequestDTO;
import com.example.tobi.himedia_shop.model.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Test
    public void testPerformanceComparison() {
        int count = 100000; // 100,000 건의 리뷰

        // 메모리 및 CPU 사용량 측정 시작
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        long startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
        long startCpuTime = getCpuTime();

        // 1. 마스터 DB에 벌크 삽입
        List<ReviewRequestDTO> masterRequestDTOs = createReviewRequestDTOs(count);

        long startInsertTimeMaster = System.currentTimeMillis();
        boolean masterInsertResult = false;
        try {
          //  masterInsertResult = reviewService.insertReviewsBulk(masterRequestDTOs);
//            @Transactional
//            public boolean insertReviewsBulk(List<ReviewRequestDTO> reviewRequestDTOs) {
//                try {
//                    List<Review> reviews = reviewRequestDTOs.stream()
//                            .map(dto -> Review.builder()
//                                    .userId(dto.getUserId())
//                                    .title(dto.getTitle())
//                                    .review(dto.getReview())
//                                    .productId(dto.getProductId())
//                                    .score(dto.getRating())
//                                    .reviewImg(dto.getReviewImage())
//                                    .build())
//                            .collect(Collectors.toList());
//                    reviewMapper.reviewInserta(reviews); // 벌크 삽입
//                    return true;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return false;
//                }
//            }
        } catch (Exception e) {
            System.err.println("Error during bulk insert: " + e.getMessage());
        }
        long elapsedInsertTimeMaster = System.currentTimeMillis() - startInsertTimeMaster;

        System.out.println("Master DB bulk insert result: " + masterInsertResult);
        System.out.println("Master DB bulk insert time for " + count + " reviews: " + elapsedInsertTimeMaster + " ms");

        // 슬레이브 DB의 데이터 복제를 기다리기 위해 대기
        waitForReplication();

        // 2. 슬레이브 DB에서 리뷰 조회
        long startSelectTimeSlave = System.currentTimeMillis();
        List<Review> slaveReviews = new ArrayList<>();
        try {
            slaveReviews = reviewService.getAllReviews();
        } catch (Exception e) {
            System.err.println("Error during fetching reviews: " + e.getMessage());
        }
        long elapsedSelectTimeSlave = System.currentTimeMillis() - startSelectTimeSlave;

        System.out.println("Total reviews retrieved from Slave DB: " + slaveReviews.size());
        System.out.println("Slave DB select time for all reviews: " + elapsedSelectTimeSlave + " ms");

        // 메모리 및 CPU 사용량 측정 종료
        long endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
        long endCpuTime = getCpuTime();

        System.out.println("Memory used: " + (endMemory - startMemory) + " bytes");
        System.out.println("CPU time used: " + (endCpuTime - startCpuTime) + " nanoseconds");
    }

    private void waitForReplication() {
        // 예시: 슬레이브 DB가 업데이트되기를 기다리는 로직
        try {
            // 실제 복제 소요 시간에 따라 조정
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private List<ReviewRequestDTO> createReviewRequestDTOs(int count) {
        List<ReviewRequestDTO> requestDTOs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ReviewRequestDTO dto = new ReviewRequestDTO();
            dto.setUserId("user" + i); // userId as String
            dto.setTitle("Review Title " + i);
            dto.setReview("This is a review content for review " + i);
            dto.setProductId(5); // productId
            dto.setRating(5); // rating
           // dto.setReviewImage("/img/reviews/t_shart.jpg"); // reviewImage
            requestDTOs.add(dto);
        }
        return requestDTOs;
    }

    private long getCpuTime() {
        return ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
    }
}
