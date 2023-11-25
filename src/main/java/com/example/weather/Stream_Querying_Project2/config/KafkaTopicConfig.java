package com.example.weather.Stream_Querying_Project2.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig
{
    @Bean
    public NewTopic javaguidesTopic()
    {
        //add .partitions(number of partitions) after ("javaguides") when you create multiple brokers
        return TopicBuilder.name("javaguides")
                .build();
    }

}
