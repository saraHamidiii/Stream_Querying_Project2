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

                            // Now you can access properties within each feature
                            JsonObject properties = feature.getAsJsonObject("properties");

                            // Print the keys of the properties object
                            //System.out.println("Properties Keys: " + properties.keySet());

                            // Fields to access within the properties
                            JsonElement textDescriptionElement = properties.get("textDescription");

                            JsonElement timeStampElement = properties.get("timestamp");

                            // Print to console
                            System.out.println("Text Description: " + textDescriptionElement.toString() + "," + " Timestamp: " + timeStampElement.toString());

                        }

                        // Extract relevant weather parameters
//                        JsonObject properties = json.getAsJsonObject("properties");
//                        double temperature = properties.getAsJsonObject("temperature").getAsDouble();
//                        double windSpeed = properties.getAsJsonObject("windSpeed").getAsDouble();
//                        double precipitationLastHour = properties.getAsJsonObject("precipitationLastHour").getAsDouble();
//
//                        // Check for specific weather events
//                        if (temperature < 0.0 && windSpeed > 20.0 && precipitationLastHour > 0.0) {
//                            LOGGER.info("Blizzard alert! - Topic: {}, Offset: {}, Key: {}, Value: {}",
//                                    record.topic(), record.offset(), record.key(), record.value());
//                            sendAlert("Blizzard Alert", record.value());
//                        } else if (windSpeed > 74.0) {
//                            LOGGER.info("Hurricane alert! - Topic: {}, Offset: {}, Key: {}, Value: {}",
//                                    record.topic(), record.offset(), record.key(), record.value());
//                            sendAlert("Hurricane Alert", record.value());
//                        } else if (precipitationLastHour > 10.0) {
//                            LOGGER.info("Heavy Rainfall Alert! - Topic: {}, Offset: {}, Key: {}, Value: {}",
//                                    record.topic(), record.offset(), record.key(), record.value());
//                            sendAlert("Heavy Rainfall Alert", record.value());
//                        }
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
    private static void sendAlert(String alertType, String message) {
        // Implement the logic to send an alert
        LOGGER.info("Sending {} alert: {}", alertType, message);
    }
}