package com.example.tobi.himedia_shop.mapper;

import com.example.tobi.himedia_shop.model.Coordinate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoordinateMapper {
    Coordinate findCoordinate(int id);

 //   String insertCoordinate(String weatherData);

}
