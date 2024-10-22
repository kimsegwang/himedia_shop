package com.example.tobi.himedia_shop.controller.whether;


import ch.qos.logback.core.model.Model;
import com.example.tobi.himedia_shop.dto.ProductListResponseDTO;
import com.example.tobi.himedia_shop.dto.WeatherRequestDTO;
import com.example.tobi.himedia_shop.dto.weather.WeatherResponseDTO;
import com.example.tobi.himedia_shop.service.RainAndTemService;
import com.example.tobi.himedia_shop.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WhetherAPiController {
    private final WeatherService weatherService;

    @PostMapping
    public WeatherResponseDTO whetherAPi(@RequestBody WeatherRequestDTO request) {


        WeatherResponseDTO weatherData = weatherService.getWeatherData(request.getNx(), request.getNy());
        return weatherData;
    }



}