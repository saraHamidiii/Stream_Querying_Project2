package com.example.weather.Stream_Querying_Project2.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/*
This class is where I created the topics for my kafka project.
 */
@Configuration
public class KafkaTopicConfig
{
    @Bean
    public NewTopic buffaloNY()
    {
        return TopicBuilder.name("buffaloNY")
                .partitions(3)
                .replicas(1)
                .build();
    }


    @Bean public NewTopic chanhassenMN()
    {
        return TopicBuilder.name("chanhassenMN")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic anchorageAK()
    {
        return TopicBuilder.name("anchorageAK")
                .partitions(3)
                .replicas(1)
                .build();
    }



}
