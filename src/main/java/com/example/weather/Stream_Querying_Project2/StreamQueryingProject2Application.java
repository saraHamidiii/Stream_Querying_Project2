package com.example.weather.Stream_Querying_Project2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;
import java.util.TimerTask;


@SpringBootApplication
public class StreamQueryingProject2Application {

	public static void main(String[] args) {
		SpringApplication.run(StreamQueryingProject2Application.class, args);

        RestTemplate restTemplate1 = new RestTemplate();
		WeatherApiClient b = new WeatherApiClient(restTemplate1);
		//System.out.println(b.getBuffaloWeather());
		//HTTPclient.connect("buffaloNY", b.getBuffaloWeather());

		RestTemplate restTemplate2 = new RestTemplate();
		WeatherApiClient c = new WeatherApiClient(restTemplate2);
		//System.out.println(c.getChanhassenWeather());
		//HTTPclient.connect("chanhassenMN", c.getChanhassenWeather());

		RestTemplate restTemplate3 = new RestTemplate();
		WeatherApiClient a = new WeatherApiClient(restTemplate3);
		//System.out.println(a.getAnchorageWeather());


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
