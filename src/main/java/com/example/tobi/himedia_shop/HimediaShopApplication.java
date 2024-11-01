package com.example.tobi.himedia_shop;

import com.example.tobi.himedia_shop.config.datasource.ReplicationDataSourceProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@MapperScan("com.example.tobi.himedia_shop.mapper")
@EnableConfigurationProperties(ReplicationDataSourceProperties.class)
public class HimediaShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(HimediaShopApplication.class, args);
	}

}
