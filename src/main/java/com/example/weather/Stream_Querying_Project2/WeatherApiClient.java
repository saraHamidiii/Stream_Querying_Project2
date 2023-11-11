package com.example.weather.Stream_Querying_Project2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WeatherApiClient {

    private final RestTemplate restTemplate;

    public WeatherApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getWeatherData(String endpoint) {
        String apiUrl = "https://api.weather.gov";
        String url = apiUrl + endpoint;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        return responseEntity.getBody();
    }

    // Add more methods as needed for specific API endpoints
}

