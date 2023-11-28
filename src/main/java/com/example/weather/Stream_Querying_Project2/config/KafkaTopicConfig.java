package com.example.weather.Stream_Querying_Project2.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig
{
    //create two more of these
    @Bean
    public NewTopic javaguidesTopic()
    {
        //add .partitions(number of partitions) after ("javaguides") when you create multiple brokers
        return TopicBuilder.name("javaguides")
                .build();
    }

    @Bean
    public NewTopic buffaloNY()
    {
        return TopicBuilder.name("buffaloNY")
                .build();
    }

    @Bean public NewTopic chanhassenMN()
    {
        return TopicBuilder.name("chanhassenMN")
                .build();
    }

    @Bean
    public NewTopic anchorageAK()
    {
        return TopicBuilder.name("anchorageAK")
                .build();
    }
    //need two more topics and delete the first topic


}
