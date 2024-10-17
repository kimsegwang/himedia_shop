package com.example.tobi.himedia_shop.service;


import com.example.tobi.himedia_shop.client.WeatherClient;
import com.example.tobi.himedia_shop.dto.weather.Item;
import com.example.tobi.himedia_shop.dto.weather.WeatherResponse;
import com.example.tobi.himedia_shop.dto.weather.WeatherResponseDTO;
import com.example.tobi.himedia_shop.mapper.ProductMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherClient weatherClient;
    private final ObjectMapper objectMapper;
    private final ProductMapper productMapper;
    @Value("${weather.api.key}")
    private String serviceKey;

    public static String getCurrentDateAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.now().format(formatter);
    }
    public static String getCurrentTimeAsString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        return LocalDateTime.now().format(timeFormatter);
    }
    public WeatherResponseDTO getWeatherData(int Usernx, int Userny){
        int numOfRows=10;
        int pageNo =1;
        String dataType = "JSON";
        String baseDate = getCurrentDateAsString();
        String baseTime  = getCurrentTimeAsString();
        int nx = Usernx;
        int ny = Userny;
        try {
            String weatherData = weatherClient.getWeatherData(
                    serviceKey,
                    numOfRows,
                    pageNo,
                    dataType,
                    baseDate,
                    baseTime,
                    nx,
                    ny
            );
            WeatherResponse weatherResponse = objectMapper.readValue(weatherData, WeatherResponse.class);

            if(weatherResponse.getResponse().getBody()==null){
                return WeatherResponseDTO.builder().build();
            }

            List<Item> items = weatherResponse.getResponse().getBody().getItems().getItem();
            Item ptyItem = items.get(0);
            Item t1hItem = items.get(3);
            var ptyValue = ptyItem.getObsrValue();
            return WeatherResponseDTO.builder()
                    .temperature(t1hItem.getObsrValue() + "℃")
                    .PrecipitationType(getPrecipitationDescription(ptyValue))
                    .build();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    private String getPrecipitationDescription(String ptyValue) {
        switch (ptyValue) {
            case "0":
                return "강수 없음";
            case "1":
                return "비";
            case "2":
                return "비 or 눈";
            case "3":
                return "눈";
            case "5":
                return "빗방울";
            case "6":
                return "빗방울눈날림";
            case "7":
                return "눈날림";
            default:
                return "알 수 없는 상태";
        }
    }
}