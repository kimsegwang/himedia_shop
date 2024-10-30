package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.dto.weather.ApiResponseDTO;
import com.example.tobi.himedia_shop.dto.weather.DataWeatherDTO;
import com.example.tobi.himedia_shop.dto.weather.DataWeatherResponseDTO;
import com.example.tobi.himedia_shop.dto.weather.WeatherResponseDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeatherApiMapper {

    void transmissionApi(ApiResponseDTO apiResponseDTO);

    DataWeatherResponseDTO getDataWeather(DataWeatherDTO dataWeatherDTO);
}
