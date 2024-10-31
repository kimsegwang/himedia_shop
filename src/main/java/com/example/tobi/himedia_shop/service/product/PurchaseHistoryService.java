package com.example.tobi.himedia_shop.service.product;

import com.example.tobi.himedia_shop.dto.history.PurchaseHistoryWithProductDTO;
import com.example.tobi.himedia_shop.mapper.HistoryMapper;
import com.example.tobi.himedia_shop.model.Products;
import com.example.tobi.himedia_shop.model.PurchaseHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class PurchaseHistoryService {
    private final HistoryMapper historyMapper;

    @Autowired
    private HistoryMapper purchaseHistoryMapper;

    // 사용자 ID를 매개변수로 받아 해당 사용자의 구매 내역을 반환
    public List<PurchaseHistoryWithProductDTO> getPurchaseHistoryWithProduct(String userId) {

        List<PurchaseHistoryWithProductDTO> purchaseHistoryWithProductByUserId = purchaseHistoryMapper.findPurchaseHistoryWithProductByUserId(userId);
        purchaseHistoryWithProductByUserId.forEach(this::processImage);
        return  purchaseHistoryWithProductByUserId;
    }
    private void processImage(PurchaseHistoryWithProductDTO product) {
        String imagePath = product.getImg();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                String dataUrl = convertImageToBase64(imagePath);
                product.setImg(dataUrl);
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
}
