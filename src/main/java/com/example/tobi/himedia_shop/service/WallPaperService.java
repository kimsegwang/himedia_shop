package com.example.tobi.himedia_shop.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class WallPaperService {

    public static String getCurrentMonthAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        return LocalDate.now().format(formatter);
    }

    public String WeatherDivide() {
        int currentMonth = Integer.parseInt(getCurrentMonthAsString()); // 월을 정수로 변환
        if (currentMonth >= 3 && currentMonth <= 5) { // 봄
            return "봄.mp4";
        } else if (currentMonth >= 6 && currentMonth <= 8) { // 여름
            return "여름.mp4";
        } else if (currentMonth >= 9 && currentMonth <= 11) { // 가을
            return "가을.mp4";
        } else { // 겨울
            return "겨울1.mp4";
        }
    }
}
