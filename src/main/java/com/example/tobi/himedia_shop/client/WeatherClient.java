package com.example.tobi.himedia_shop.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="weatherClient",url = "${weather.api.url}")
public interface WeatherClient {
    @GetMapping
    String getWeatherData(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("numOfRows") int numOfRows,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("dataType") String dataType,
            @RequestParam("base_date") String base_date,
            @RequestParam("base_time") String base_time,
            @RequestParam("nx") int nx,
            @RequestParam("ny") int ny
    );
}