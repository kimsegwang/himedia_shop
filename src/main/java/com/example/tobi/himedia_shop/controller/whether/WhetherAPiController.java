package com.example.tobi.himedia_shop.controller.whether;


import com.example.tobi.himedia_shop.dto.product.product.ProductListResponseDTO;
import com.example.tobi.himedia_shop.dto.WeatherRequestDTO;
import com.example.tobi.himedia_shop.dto.weather.WeatherResponseDTO;
import com.example.tobi.himedia_shop.service.CoordinateService;
import com.example.tobi.himedia_shop.service.RainAndTemService;
import com.example.tobi.himedia_shop.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WhetherAPiController {
    private final WeatherService weatherService;
    private final CoordinateService coordinateService;

    @PostMapping
    public WeatherResponseDTO whetherAPi(@RequestBody WeatherRequestDTO request) {


        WeatherResponseDTO weatherData = weatherService.getDataWeather(request.getNx(), request.getNy());
        return weatherData;
    }


}