package com.example.tobi.himedia_shop.service;

import com.example.tobi.himedia_shop.dto.product.product.ProductListResponseDTO;
import com.example.tobi.himedia_shop.dto.RainAndTemResponseDTO;
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
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RainAndTemService {
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public List<ProductListResponseDTO> Divide(int tem, int rain) {
        RainAndTemResponseDTO build = RainAndTemResponseDTO.builder().temperature(tem).precipitation(rain).build();
        List<Products> productWeather = productMapper.getProductWeather(build);

        return productWeather.stream()
                .map(products -> ProductListResponseDTO.builder()
                        .title(products.getTitle())
                        .price(products.getPrice())
                        .contentImg(processImage(products.getContentImg()))
                        .build())
                .collect(Collectors.toList());
    }

    private String processImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                return convertImageToBase64(imagePath);
            } catch (IOException e) {
                throw new RuntimeException("Error reading image file: " + imagePath, e);
            }
        }
        return ""; // 빈 문자열 반환 또는 기본 이미지 URL 설정
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
}
