package com.example.tobi.himedia_shop.model;

import com.example.tobi.himedia_shop.dto.coordinate.CoordinateResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Coordinate {
    private int nx;
    private int ny;

    public CoordinateResponseDTO toCoordinateResponseDTO() {
        return CoordinateResponseDTO.builder()
                .nx(nx)
                .ny(ny)
                .build();
    }

}
