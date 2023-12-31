package com.example.weather.Stream_Querying_Project2.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//I use this class to send a message to my kafka project.
@Service
public class KafkaProducer
{
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
    private KafkaTemplate<String, String> kafkaTemplate;


    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //I give the kafka topic that I want to send the message to and the message.
    public void sendMessage(String topic, String message)
    {
        LOGGER.info(String.format("Message sent %s", message));
        kafkaTemplate.send(topic, message);
    }
}
