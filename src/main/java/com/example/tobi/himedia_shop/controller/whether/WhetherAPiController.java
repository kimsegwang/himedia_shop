package com.example.tobi.himedia_shop.controller.whether;


import com.example.tobi.himedia_shop.dto.WeatherRequestDTO;
import com.example.tobi.himedia_shop.dto.weather.WeatherResponseDTO;
import com.example.tobi.himedia_shop.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WhetherAPiController {
    private final WeatherService weatherService;

    @PostMapping
    public WeatherResponseDTO whetherAPi(@RequestBody WeatherRequestDTO request) {
        return weatherService.getWeatherData(request.getNx(),request.getNy());
    }
}