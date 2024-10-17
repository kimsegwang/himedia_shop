package com.example.tobi.himedia_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication

public class HimediaShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(HimediaShopApplication.class, args);
	}

}
