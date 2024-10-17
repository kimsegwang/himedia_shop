package com.example.tobi.himedia_shop.dto.weather;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Items {
    private List<Item> item;
}