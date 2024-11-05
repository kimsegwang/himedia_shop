package com.example.tobi.himedia_shop.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.ImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

@Service
public class FileService {
    private final String UPLOADED_FOLDER = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "project" + File.separator + "upload" + File.separator + "img" + File.separator;

    // 파일 저장 로직
    public String fileUpload(MultipartFile file, String paths) {
        // 업로드 디렉토리 생성
        File uploadDir = new File(UPLOADED_FOLDER + paths);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // 디렉토리 생성
        }

        // 파일 이름 생성 (중복 방지)
        String originalFilename1 = file.getOriginalFilename();
        assert originalFilename1 != null;
        String originalFilename = originalFilename1.replace(" ", "_");
        String safeFilename = originalFilename;
        int count = 1;

        while (new File(UPLOADED_FOLDER + paths + safeFilename).exists()) {
            String fileNameWithoutExt = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String fileExtension = ".webp"; // 확장자를 webp로 고정
            safeFilename = fileNameWithoutExt + "_" + count++ + fileExtension; // 이름 변경
        }

        try {
            // MultipartFile을 BufferedImage로 변환
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

            // WebP 형식으로 파일 저장
            Path path = Paths.get(UPLOADED_FOLDER + paths + safeFilename);
            saveAsWebP(bufferedImage, path.toFile());

            return UPLOADED_FOLDER + paths.replaceFirst("^/", "") + safeFilename; // 상대 경로로 반환
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드에 실패했습니다: " + e.getMessage(), e);
        }
    }

    private void saveAsWebP(BufferedImage image, File outputFile) throws IOException {
        // WebP 이미지 저장을 위한 ImageWriter 설정
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("webp");
        if (!writers.hasNext()) {
            throw new IllegalArgumentException("WebP writer not found");
        }

        ImageWriter writer = writers.next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile);
        writer.setOutput(ios);

        // 옵션 설정 (예: 품질 설정)
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(1.0f); // 최대 품질 (0.0f ~ 1.0f)

        writer.write(null, new javax.imageio.IIOImage(image, null, null), param);
        ios.close();
        writer.dispose();
    }
}
