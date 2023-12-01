package com.example.weather.Stream_Querying_Project2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;
import java.util.TimerTask;

/*
In this class, I created 3 WeatherApiClient objects; one for each topic.
The topics in this project are cities in the US that are most likely to experience blizzards.
The cities I chose are: (Buffalo, NY), (Chanhassen, MN), and (Anchorage, AK).
I used the connect function in the HTTPclient class to post the JSON with the weather data
of the given city in the kafka. I fetch the weather data from each city every hour.
* */
@SpringBootApplication
public class StreamQueryingProject2Application {

	public static void main(String[] args) {
		SpringApplication.run(StreamQueryingProject2Application.class, args);

		//creating WeatherApiClient objects for each topic
        RestTemplate restTemplate1 = new RestTemplate();
		WeatherApiClient b = new WeatherApiClient(restTemplate1);


		RestTemplate restTemplate2 = new RestTemplate();
		WeatherApiClient c = new WeatherApiClient(restTemplate2);


		RestTemplate restTemplate3 = new RestTemplate();
		WeatherApiClient a = new WeatherApiClient(restTemplate3);



		Timer timer = new Timer();

		TimerTask publishTask = new TimerTask()
		{
			@Override
			public void run()
			{
				HTTPclient.connect("buffaloNY", b.getBuffaloWeather());
				HTTPclient.connect("chanhassenMN", c.getChanhassenWeather());
				HTTPclient.connect("anchorageAK", a.getAnchorageWeather());


			}
		};

		//fetches data every hour from api
		timer.scheduleAtFixedRate(publishTask, 0, 3600000);






	}

}
