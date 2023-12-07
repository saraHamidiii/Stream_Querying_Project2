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

@Service
public class MyKafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyKafkaConsumer.class);
    public static void consumeMessages() {
        LOGGER.info("Kafka Consumer is starting...");

        // Set up consumer properties
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092"); // Replace with your Kafka bootstrap servers
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

                            // Print the properties to the console
                            //System.out.println("Properties: " + properties.toString());

                            // Print the keys of the properties object
                            //System.out.println("Properties Keys: " + properties.keySet());

                            // Fields to access within the properties
                            JsonElement idElement = properties.get("@id");

                            JsonElement textDescriptionElement = properties.get("textDescription");

                            // Fields to access within temperature
                            JsonElement valueElement = temperature.get("value");

                            // Check different weather conditions and display respective alerts
                            if (textDescriptionElement != null) {
                                String textDescription = textDescriptionElement.getAsString();

                                if ("clear".equalsIgnoreCase(textDescription)) {
                                    System.out.println("ID: " + idElement + " Text Description: Clear");
                                    sendClearWeatherAlert();
                                } else if ("mostly cloudy".equalsIgnoreCase(textDescription)) {
                                    System.out.println("ID: " + idElement + " Text Description: Mostly Cloudy");
                                    sendMostlyCloudyAlert();
                                } else {
                                    // Handle other conditions or print to console
                                    System.out.println("ID: " + idElement + " Text Description: " + textDescription);
                                }
                            }

                            // Print to console
                            //System.out.println("ID: " + idElement + "Text Description: " + textDescriptionElement.toString() + "," + " Temperature: " + valueElement.toString());
                        }

                        //System.out.printf("Topic = %s, Offset = %d, Key = %s, Value = %s%n",
//                        LOGGER.info("Consumed message - Topic: {}, Offset: {}, Key: {}, Value: {}",
//                                record.topic(), record.offset(), record.key(), record.value());
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
}