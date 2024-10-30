package com.example.tobi.himedia_shop.dto.weather;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DataWeatherDTO {

    private int nx;
    private int ny;
    private int basedate;
}
