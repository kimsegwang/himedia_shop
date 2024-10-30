package com.example.tobi.himedia_shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RainAndTemResponseDTO {
    private int temperature;
    private int precipitation;
}
