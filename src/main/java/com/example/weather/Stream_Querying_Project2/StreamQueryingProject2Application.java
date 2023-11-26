package com.example.weather.Stream_Querying_Project2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class StreamQueryingProject2Application {

	public static void main(String[] args) {
		SpringApplication.run(StreamQueryingProject2Application.class, args);

        RestTemplate restTemplate = new RestTemplate();
		WeatherApiClient obj = new WeatherApiClient(restTemplate);
		System.out.println(obj.getBuffaloWindSpeeds());
		HTTPclient.connect(obj.getBuffaloWindSpeeds());
	}

}
