package com.example.tobi.himedia_shop.scheduled;

import com.example.tobi.himedia_shop.model.Coordinate;
import com.example.tobi.himedia_shop.service.CoordinateService;
import com.example.tobi.himedia_shop.service.WeatherService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Thread.sleep;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfiguration {
    private final CoordinateService coordinateService;
    private final WeatherService weatherService;

    @Scheduled(cron = "0 10 16 * * *")
    public void run() throws InterruptedException {

        List<List<Integer>> region = coordinateService.findCoordinate();
        System.out.println("region :: " + region);
        for (List<Integer> coordinate : region) {
            weatherService.getWeatherData(coordinate.get(0), coordinate.get(1));
            System.out.println(coordinate.get(0));
            System.out.println(coordinate.get(1));
            sleep(1000);
        }
    }
}
