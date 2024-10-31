package com.example.tobi.himedia_shop.service;


import com.example.tobi.himedia_shop.client.WeatherClient;
import com.example.tobi.himedia_shop.dto.weather.*;
import com.example.tobi.himedia_shop.mapper.CoordinateMapper;
import com.example.tobi.himedia_shop.mapper.ProductMapper;
import com.example.tobi.himedia_shop.mapper.WeatherApiMapper;

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
    private final CoordinateMapper coordinateMapper;
    private final WeatherApiMapper weatherApiMapper;

    @Value("${weather.api.key}")
    private String serviceKey;
    private final RainAndTemService rainAndTemService;

    public static String getCurrentDateAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.now().format(formatter);
    }

    public static String getCurrentTimeAsString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        return LocalDateTime.now().format(timeFormatter);
    }

    public ApiResponseDTO setEverything(int nx, int ny, String information, String baseDate) {
        return ApiResponseDTO.builder()
                .nx(nx)
                .ny(ny)
                .information(information)
                .basedate(Integer.parseInt(baseDate))
                .build(); // 빌더를 사용하여 객체를 생성하고 반환합니다.
    }

    public DataWeatherDTO setEverything(int nx, int ny, String baseDate) {
        return DataWeatherDTO.builder()
                .nx(nx)
                .ny(ny)
                .basedate(Integer.parseInt(baseDate))
                .build(); // 빌더를 사용하여 객체를 생성하고 반환합니다.
    }

    public WeatherResponseDTO getDataWeather(int nx, int ny) {
        DataWeatherDTO dataWeatherDTO = setEverything(nx, ny, getCurrentDateAsString());
        DataWeatherResponseDTO a = weatherApiMapper.getDataWeather(dataWeatherDTO);

        try {

            WeatherResponse weatherResponse = objectMapper.readValue(a.getInformation(), WeatherResponse.class);
            System.out.println(a.getInformation());
            if (weatherResponse.getResponse().getBody() == null) {
                return WeatherResponseDTO.builder().build();
            }
            System.out.println(weatherResponse.getResponse().getBody());

            List<Item> items = weatherResponse.getResponse().getBody().getItems().getItem();
            Item ptyItem = items.get(0);
            Item t1hItem = items.get(3);
            var ptyValue = ptyItem.getObsrValue();
            int tem = TemperatureDivide(Float.valueOf(t1hItem.getObsrValue()));
            int rain = Integer.parseInt(ptyValue) == 0 ? 0 : 1;

            return WeatherResponseDTO.builder()
                    .temperature(t1hItem.getObsrValue() + "℃")
                    .PrecipitationType(getPrecipitationDescription(ptyValue))
                    .products(rainAndTemService.Divide(tem, rain))
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

    private int TemperatureDivide(Float t1hValue) {
        if (t1hValue > 23) {
            return 1;
        } else if (t1hValue > 15) {
            return 2;
        } else if (t1hValue > 10) {
            return 3;
        } else if (t1hValue > 5) {
            return 4;
        } else {
            return 5;
        }
    }


    public WeatherResponseDTO getWeatherData(int Usernx, int Userny) {


        int numOfRows = 10;
        int pageNo = 1;
        String dataType = "JSON";
        String baseDate = getCurrentDateAsString();
        String baseTime = getCurrentTimeAsString();
        int nx = Usernx;
        int ny = Userny;

        String weatherData = weatherClient.getWeatherData(serviceKey,
                numOfRows,
                pageNo,
                dataType,
                baseDate,
                baseTime,
                nx,
                ny
        );


        ApiResponseDTO apiResponseDTO = setEverything(nx, ny, weatherData, baseDate);

        weatherApiMapper.transmissionApi(apiResponseDTO);
        // System.out.println("저장@@@@@@@@@@@@@@@@@@@@");
        System.out.println("weatherData :: " + weatherData);
        return null;

    }
}