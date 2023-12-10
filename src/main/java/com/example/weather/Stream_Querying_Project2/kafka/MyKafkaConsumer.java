package com.example.weather.Stream_Querying_Project2.kafka;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.springframework.stereotype.Service;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

/* The consumer class fetches and parses data from the Kafka topic
*  Alerts are displayed based on weather conditions */
@Service
public class MyKafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyKafkaConsumer.class);
    public static void consumeMessages() {
        LOGGER.info("Kafka Consumer is starting...");

        // Set up consumer properties
        Properties props = new Properties();
        props.put("bootstrap.servers", "34.66.182.16:9092, 34.171.82.244:9092, 34.123.131.116:9092"); // Replace with your Kafka bootstrap servers
        props.put("group.id", "my-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest"); // start at the end of the topic

        // Create a Kafka consumer
        try (Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
            // Subscribe to the Kafka topics
            consumer.subscribe(Arrays.asList("buffaloNY", "chanhassenMN", "anchorageAK"));
            LOGGER.info("Subscribed to Kafka topics");

            // Ensure the consumer joins the group and receives its assignment
            consumer.poll(Duration.ofMillis(0));

            // Seek to the end of each partition
            consumer.assignment().forEach(tp -> consumer.seekToEnd(Collections.singleton(tp)));
            
            // Continuously poll for new data from kafka topics listening for incoming messages and processing them
            while (true) {
                // Poll for new data
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                LOGGER.debug("Polled for new data");

                // Process the received messages
                for (ConsumerRecord<String, String> record : records) {
                    try {
                        // Parse the JSON message
                        JsonObject json = JsonParser.parseString(record.value()).getAsJsonObject();

                        // Access the "features" array
                        JsonArray featuresArray = json.getAsJsonArray("features");

                        for (JsonElement featureElement : featuresArray) {
                            JsonObject feature = featureElement.getAsJsonObject();

                            // Access properties within each feature
                            JsonObject properties = feature.getAsJsonObject("properties");

                            // Access temperature within properties
                            JsonObject temperature = properties.getAsJsonObject("temperature");

                            // Fields to access within the properties
                            JsonElement idElement = properties.get("@id");

                            JsonElement textDescriptionElement = properties.get("textDescription");

                            // Fields to access within temperature
                            JsonElement valueElement = temperature.get("value");

                            // Check different weather conditions and display respective alerts
                            if (textDescriptionElement != null) {
                                String textDescription = textDescriptionElement.getAsString();

                                if ("clear".equalsIgnoreCase(textDescription)) {
                                    System.out.println("ID: " + idElement + " Text Description: Clear" + "," + " Temperature: " + valueElement);
                                    sendClearWeatherAlert();
                                } else if ("mostly cloudy".equalsIgnoreCase(textDescription)) {
                                    System.out.println("ID: " + idElement + " Text Description: Mostly Cloudy" + "," + " Temperature: " + valueElement);
                                    sendMostlyCloudyAlert();
                                } else if ("light snow".equalsIgnoreCase(textDescription)) {
                                    System.out.println("ID: " + idElement + " Text Description: Light Snow" + "," + " Temperature: " + valueElement);
                                    sendLightSnowAlert();
                                } else if ("fog".equalsIgnoreCase(textDescription)) {
                                    System.out.println("ID: " + idElement + " Text Description: Fog" + "," + " Temperature: " + valueElement);
                                    sendFogAlert();
                                } else {
                                    // Handle other conditions or print to console
                                    System.out.println("ID: " + idElement + " Text Description: " + textDescription + "," + " Temperature: " + valueElement);
                                }
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error("Error in Kafka Consumer: {}", e.getMessage());
                    } finally {
                        LOGGER.info("Kafka Consumer has ended.");
                    }
                }
            }
        }
    }
    // Alert method for clear weather
    private static void sendClearWeatherAlert() {
        // Implement the logic to send an alert for clear weather
        System.out.println("Clear Weather Alert: Enjoy the clear skies!");
    }

    // Alert method for mostly cloudy weather
    private static void sendMostlyCloudyAlert() {
        // Implement the logic to send an alert for mostly cloudy weather
        System.out.println("Mostly Cloudy Weather Alert: It may be overcast!");
    }

    // Alert method for light snow
    private static void sendLightSnowAlert() {
        // Implement the logic to send an alert for light snow
        System.out.println("Light Snow Alert: Have a sip of hot cocoa and enjoy the winter wonderland!");
    }
    // Alert method for fog
    private static void sendFogAlert() {
        // Implement the logic to send an alert for fog
        System.out.println("Fog Alert: Foggy vibes ahead. Slow down and stay safe!");
    }
}