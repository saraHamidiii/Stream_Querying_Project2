package com.example.weather.Stream_Querying_Project2.controller;

import com.example.weather.Stream_Querying_Project2.kafka.KafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//In this class, I created a KafkaProducer and I call the sendMessage method on it
//to post the message to the kafka publish endpoint.
@RestController
@RequestMapping("/api/v1/kafka")
public class MessageController
{
    public KafkaProducer kafkaProducer;
    public static String topic = "";

    public MessageController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    //Here, I am posting my message to the publish endpoint of the kafka url.
    //If I am successful, it will say: "Message sent to the topic".
    @PostMapping ("/publish")
    public ResponseEntity<String> publish(@RequestBody String message){
        kafkaProducer.sendMessage(topic, message);
        return ResponseEntity.ok("Message sent to the topic");
    }
}
