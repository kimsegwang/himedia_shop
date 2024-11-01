package com.example.tobi.himedia_shop.service;

import com.example.tobi.himedia_shop.dto.coordinate.CoordinateResponseDTO;
import com.example.tobi.himedia_shop.mapper.CoordinateMapper;
import com.example.tobi.himedia_shop.model.Coordinate;
import com.example.tobi.himedia_shop.scheduled.SchedulerConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.endpoint.event.RefreshEventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoordinateService {
    private final CoordinateMapper coordinateMapper;
    private final RefreshEventListener refreshEventListener;
    private final WeatherService weatherService;

    //nx,ny 셀렉트 관련 서비스
    public List<List<Integer>> findCoordinate() {
        List<List<Integer>> coordinates = new ArrayList<>();
//        List<Integer> region = new ArrayList<>();
        for (int i = 1; i < 19; i++) {
            List<Integer> region = new ArrayList<>();
            Coordinate coordinate = coordinateMapper.findCoordinate(i);
            region.add(coordinate.getNx());
            region.add(coordinate.getNy());
            coordinates.add(region);
        }
        return coordinates;
    }
}
