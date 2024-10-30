package com.example.tobi.himedia_shop.model;

import com.example.tobi.himedia_shop.dto.weather.ApiResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeatherApi {

    private int id;
    private String information;
    private int nx;
    private int ny;
    private int baseDate;

}
