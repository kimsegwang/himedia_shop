package com.example.tobi.himedia_shop.dto.coordinate;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CoordinateResponseDTO {
    private int id;
    private int nx;
    private int ny;

}