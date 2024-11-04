package com.example.tobi.himedia_shop.service;

import com.example.tobi.himedia_shop.model.Products;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Iterator;
@Component
public class ImageBase64Converter {

    public void processImage(Products product) {
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

    public String convertImageToBase64(String imagePath) throws IOException {
        String imageFormat = getImageFormat(imagePath);
        Path path = Path.of(imagePath);
        byte[] bytes = Files.readAllBytes(path);
        String base64Image = Base64.getEncoder().encodeToString(bytes);
        return "data:image/" + imageFormat + ";base64," + base64Image;
    }

    public String getImageFormat(String imagePath) {
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
