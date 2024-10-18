package com.example.tobi.himedia_shop.dto.weather;

import com.example.tobi.himedia_shop.dto.ProductListResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class WeatherResponseDTO {
    private String temperature;
    private String PrecipitationType;
    private List<ProductListResponseDTO> products;


}