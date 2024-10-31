package com.example.tobi.himedia_shop.service.product;

import com.example.tobi.himedia_shop.dto.product.review.PageResponseDTO;
import com.example.tobi.himedia_shop.dto.product.review.ReviewQueryDTO;
import com.example.tobi.himedia_shop.dto.product.review.ReviewRequestDTO;
import com.example.tobi.himedia_shop.dto.product.review.ReviewResponseDTO;
import com.example.tobi.himedia_shop.mapper.ReviewMapper;
import com.example.tobi.himedia_shop.model.Products;
import com.example.tobi.himedia_shop.model.Review;
import com.example.tobi.himedia_shop.service.admin.FileService;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewMapper reviewMapper;
    private final FileService fileService;

    // Caffeine 캐시 설정
    private final Cache<String, PageResponseDTO> reviewCache = Caffeine.newBuilder()
            .maximumSize(10_000) // 최대 캐시 크기
            .expireAfterAccess(10, TimeUnit.MINUTES) // 접근 후 만료 시간
            .build();

    public List<Review> getAllReviews() {
        return reviewMapper.getAllReviewsa(); // 모든 리뷰 조회
    }
    @Transactional
    public boolean insertReview(ReviewRequestDTO requestDTO) {
        String path = null;
        if (requestDTO.getReviewImage() != null && !requestDTO.getReviewImage().isEmpty()) {
            path = fileService.fileUpload(requestDTO.getReviewImage(), "/reviews/");
        }
        Review review = Review.builder()
                .userId(requestDTO.getUserId())
                .title(requestDTO.getTitle())
                .review(requestDTO.getReview())
                .productId(requestDTO.getProductId())
                .score(requestDTO.getRating())
                .reviewImg(path)
                .build();
        boolean success = reviewMapper.reviewInsert(review) > 0;
        if (success) {
            // 캐시 무효화
            int totalReviews = reviewMapper.countReviews(requestDTO.getProductId());
            int totalPages = (int) Math.ceil((double) totalReviews / 5);
            for (int i = 0; i < totalPages; i++) {
                reviewCache.invalidate("product_" + requestDTO.getProductId() + "_page_" + i);
            }
        }
        return success;
    }

    @Transactional(readOnly = true)
    public PageResponseDTO getReviewsForProduct(int productId, int page, int size) {
        String cacheKey = "product_" + productId + "_page_" + page; // 캐시 키 생성

        // 캐시에서 결과를 찾음
        PageResponseDTO cachedResponse = reviewCache.getIfPresent(cacheKey);
        if (cachedResponse != null) {
            return cachedResponse; // 캐시가 존재할 경우 캐시 반환
        }

        // DB에서 데이터 조회
        ReviewQueryDTO query = ReviewQueryDTO.builder()
                .productId(productId)
                .limit(size)
                .offset(page * size)
                .build();

        List<Review> allReviews = reviewMapper.getAllReviews(query);
        List<ReviewResponseDTO> reviews = convertToReviewResponseDTO(allReviews);

        int totalReviews = reviewMapper.countReviews(productId);
        int totalPages = (int) Math.ceil((double) totalReviews / size);

        reviews.forEach(this::processImage);
        PageResponseDTO response = PageResponseDTO.builder()
                .reviews(reviews)
                .totalReviews(totalReviews)
                .totalPages(totalPages)
                .build();

        // 결과를 캐시에 저장
        reviewCache.put(cacheKey, response);

        return response;
    }
    private void processImage(ReviewResponseDTO review) {
        String imagePath = review.getReviewImg();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                String dataUrl = convertImageToBase64(imagePath);
                review.setReviewImg(dataUrl);
            } catch (IOException e) {
                throw new RuntimeException("Error reading image file: " + imagePath, e);
            }
        }
    }

    private String convertImageToBase64(String imagePath) throws IOException {
        String imageFormat = getImageFormat(imagePath);
        Path path = Path.of(imagePath);
        byte[] bytes = Files.readAllBytes(path);
        String base64Image = Base64.getEncoder().encodeToString(bytes);
        return "data:image/" + imageFormat + ";base64," + base64Image;
    }
    public static String getImageFormat(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                return reader.getFormatName().toLowerCase(); // 이미지 포맷 반환 (소문자로 변환)
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "png"; // 포맷을 확인할 수 없는 경우
    }
    @Transactional
    public boolean deleteReview(int reviewId) {
        return reviewMapper.reviewDelete(reviewId) > 0;
    }

    private List<ReviewResponseDTO> convertToReviewResponseDTO(List<Review> reviews) {
        return reviews.stream()
                .map(review -> ReviewResponseDTO.builder()
                        .id(review.getId())
                        .productId(review.getProductId())
                        .userId(review.getUserId())
                        .title(review.getTitle())
                        .review(review.getReview())
                        .reviewDate(review.getReviewDate())
                        .score(review.getScore())
                        .reviewImg(review.getReviewImg())
                        .build())
                .collect(Collectors.toList());
    }




}
