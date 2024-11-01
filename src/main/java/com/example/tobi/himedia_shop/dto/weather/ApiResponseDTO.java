package com.example.tobi.himedia_shop.dto.weather;

import lombok.*;

@Getter
@Setter
@Builder
public class ApiResponseDTO {

    private String information;
    private int nx;
    private int ny;
    private int basedate;

}