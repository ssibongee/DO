package com.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@MapperScan(basePackages = {"com.backend.mapper"}) // proxy 생성.
public class BackendApplication implements WebMvcConfigurer {

	public static void main(String[] args) {

		SpringApplication.run(BackendApplication.class, args);

	}
}
