package com.example.tobi.himedia_shop.service.product;

import com.example.tobi.himedia_shop.dto.product.product.ProductDetailResponseDTO;
import com.example.tobi.himedia_shop.dto.product.product.ProductListResponseDTO;
import com.example.tobi.himedia_shop.mapper.ProductMapper;
import com.example.tobi.himedia_shop.model.Products;
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
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public ProductDetailResponseDTO getProductById(int productId) {
        Products productById = productMapper.getProductById(productId);
        processImage(productById);

        return buildProductDetailResponse(productById);
    }

    @Transactional(readOnly = true)
    public List<ProductListResponseDTO> getAllProducts() {
        List<Products> productALL = productMapper.getProductALL();
        productALL.forEach(this::processImage);
        Collections.shuffle(productALL);
        return productALL.stream()
                .limit(3)
                .map(this::buildProductListResponse)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<ProductListResponseDTO> getProductRecommend(int productId) {
        List<Products> productALL = productMapper.getProductRecommend(productId);
        productALL.forEach(this::processImage);
        //리스트 셔플
        Collections.shuffle(productALL);
        return productALL.stream()
                .limit(5)
                .map(this::buildProductListResponse)
                .collect(Collectors.toList());
    }

    private void processImage(Products product) {
        String imagePath = product.getContentImg();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                String dataUrl = convertImageToBase64(imagePath);
                product.setContentImg(dataUrl);
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
        return "png"; // 기본값 설정 (확인할 수 없는 경우)
    }
    private ProductDetailResponseDTO buildProductDetailResponse(Products product) {
        return ProductDetailResponseDTO.builder()
                .id(product.getId())
                .category(product.getCategory())
                .title(product.getTitle())
                .content(product.getContent())
                .sellerId(product.getSellerId())
                .created(product.getCreated())
                .price(product.getPrice())
                .img(product.getContentImg()) // Base64 데이터가 설정된 이미지
                .stock(product.getStock())
                .build();
    }

    private ProductListResponseDTO buildProductListResponse(Products product) {
        return ProductListResponseDTO.builder()
                .id(product.getId())
                .category(product.getCategory())
                .title(product.getTitle())
                .content(product.getContent())
                .sellerId(product.getSellerId())
                .created(product.getCreated())
                .price(product.getPrice())
                .contentImg(product.getContentImg())
                .stock(product.getStock())
                .build();
    }


}
