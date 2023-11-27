package com.example.weather.Stream_Querying_Project2.controller;

import com.example.weather.Stream_Querying_Project2.kafka.KafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
public class MessageController
{
    public KafkaProducer kafkaProducer;
    public static String topic = "";

    public MessageController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    //http:localhost:8080/api/v1/kafka/publish?message=hello world
    @PostMapping ("/publish")
    public ResponseEntity<String> publish(@RequestBody String message){
        kafkaProducer.sendMessage(topic, message);
        return ResponseEntity.ok("Message sent to the topic");
    }
}
