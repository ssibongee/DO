package com.backend;

import com.backend.Interceptor.JwtInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@SpringBootApplication
@MapperScan(basePackages = {"com.backend.mapper"}) // proxy 생성.
public class BackendApplication implements WebMvcConfigurer {

	public static void main(String[] args) {

		SpringApplication.run(BackendApplication.class, args);

	}
	@Autowired
	private JwtInterceptor jwtInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/signin/") // 기본 적용 경로
				.addPathPatterns("/api/info/");
//				.excludePathPatterns(Arrays.asList("/api/signin/**")) // 제외 경로
//				.excludePathPatterns(Arrays.asList("/api/info/**"));
	}

	@Override
	public void addCorsMappings(CorsRegistry registry){
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*")
				.allowedHeaders("*")
				.exposedHeaders("jwt-auth-token");
	}
}
