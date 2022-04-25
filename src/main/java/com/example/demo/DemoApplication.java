package com.example.demo;

import com.example.demo.requests.RestRequests;
import com.example.demo.services.WeatherService;
import com.google.maps.errors.ApiException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class DemoApplication {
	public static void main(String[] args) throws IOException, InterruptedException, ApiException {
		SpringApplication.run(DemoApplication.class, args);
	}

}
