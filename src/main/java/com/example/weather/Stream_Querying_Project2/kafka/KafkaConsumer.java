//package com.example.weather.Stream_Querying_Project2.kafka;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//
//
//@Service
//public class KafkaConsumer {
//    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
//
//
//    @KafkaListener(topics = "topic", groupId = "${spring.kafka.consumer.group-id}")
//    public void consume(String message){
//        LOGGER.info(String.format("Message received -> %s", message));
//    }
//}
