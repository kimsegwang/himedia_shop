package com.example.tobi.himedia_shop.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

        // 중복된 파일명이 존재하면 수정된 이름으로 생성
        while (new File(UPLOADED_FOLDER + paths + safeFilename).exists()) {
            String fileNameWithoutExt = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.')); // 원본 파일 확장자 유지
            safeFilename = fileNameWithoutExt + "_" + count++ + fileExtension; // 이름 변경
        }

        try {
            // 파일을 지정된 경로에 저장
            File outputFile = new File(UPLOADED_FOLDER + paths + safeFilename);
            file.transferTo(outputFile); // MultipartFile을 실제 파일로 저장

            return UPLOADED_FOLDER + paths.replaceFirst("^/", "") + safeFilename; // 상대 경로로 반환
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드에 실패했습니다: " + e.getMessage(), e);
        }
    }
}
