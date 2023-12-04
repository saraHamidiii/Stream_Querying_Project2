package com.example.weather.Stream_Querying_Project2;

import com.example.weather.Stream_Querying_Project2.kafka.MyKafkaConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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
@EnableScheduling
public class StreamQueryingProject2Application {

	@Autowired
	private MyKafkaConsumer myKafkaConsumer;

	//creating WeatherApiClient objects for each topic
	private final WeatherApiClient b = new WeatherApiClient(new RestTemplate());
	private final WeatherApiClient c = new WeatherApiClient(new RestTemplate());
	private final WeatherApiClient a = new WeatherApiClient(new RestTemplate());

	public static void main(String[] args) {
		SpringApplication.run(StreamQueryingProject2Application.class, args);

		// Create an instance of the main class to access non-static methods
		StreamQueryingProject2Application application = new StreamQueryingProject2Application();

		// Explicitly invoke the scheduled methods
		application.fetchDataAndPublishToKafka();
		application.startKafkaConsumer();
	}

	// Scheduled task to fetch data every hour from API and publish to Kafka
	@Scheduled(fixedRate = 3600000)
	private void fetchDataAndPublishToKafka() {
		HTTPclient.connect("buffaloNY", b.getBuffaloWeather());
		HTTPclient.connect("chanhassenMN", c.getChanhassenWeather());
		HTTPclient.connect("anchorageAK", a.getAnchorageWeather());
	}

	// Scheduled task to start Kafka consumer
	@Scheduled(fixedRate = 1000) // Adjust the rate as needed
	private void startKafkaConsumer() {
		myKafkaConsumer.consumeMessages();
	}
}

