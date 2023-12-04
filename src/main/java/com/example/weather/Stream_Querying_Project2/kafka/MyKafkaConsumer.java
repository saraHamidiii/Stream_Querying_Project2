package com.example.weather.Stream_Querying_Project2.kafka;

import org.springframework.stereotype.Service;
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
//    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
//
//
//    @KafkaListener(topics = "topic", groupId = "${spring.kafka.consumer.group-id}")
//    public void consume(String message){
//        LOGGER.info(String.format("Message received -> %s", message));
//    }
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

            // Ensure the consumer joins the group and receives its assignment
            consumer.poll(Duration.ofMillis(0));

            // Seek to the end of each partition
            consumer.assignment().forEach(tp -> consumer.seekToEnd(Collections.singleton(tp)));

            while (true) {
                // Poll for new data
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                // Process the received messages
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Topic = %s, Offset = %d, Key = %s, Value = %s%n",
                            record.topic(), record.offset(), record.key(), record.value());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error in Kafka Consumer: {}", e.getMessage());
        } finally {
            LOGGER.info("Kafka Consumer has ended.");
        }
    }
}

