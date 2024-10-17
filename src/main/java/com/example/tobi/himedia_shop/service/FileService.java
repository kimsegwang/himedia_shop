package com.example.tobi.himedia_shop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private final String UPLOADED_FOLDER = "src/main/resources/static/img/"; // 저장할 경로

    // 파일 저장 로직
    public String fileUpload(MultipartFile file) {
        // 업로드 디렉토리 생성
        File uploadDir = new File(UPLOADED_FOLDER);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // 디렉토리 생성
        }

        // 파일 이름 생성 (중복 방지)
        String originalFilename1 = file.getOriginalFilename();
        assert originalFilename1 != null;
        String originalFilename = originalFilename1.replace(" ", "_");
        String safeFilename = originalFilename;
        int count = 1;

        while (new File(UPLOADED_FOLDER + safeFilename).exists()) {
            assert originalFilename != null;
            String fileNameWithoutExt = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            safeFilename = fileNameWithoutExt + "_" + count++ + fileExtension; // 이름 변경
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + safeFilename);
            Files.write(path, bytes);
            return "img/" + safeFilename; // 상대 경로로 반환
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드에 실패했습니다: " + e.getMessage(), e);
        }
    }
}
