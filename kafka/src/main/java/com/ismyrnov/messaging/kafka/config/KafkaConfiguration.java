package com.ismyrnov.messaging.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {
  public static final String TOPIC = "topic-1";
  public static final String TAXI_TOPIC = "taxi-topic-1";

  @Bean
  public NewTopic topic() {
    return TopicBuilder.name(TOPIC)
        .partitions(3)
        .replicas(1)
        .build();
  }

  @Bean
  public NewTopic topicTaxi() {
    return TopicBuilder.name(TAXI_TOPIC)
        .partitions(2)
        .replicas(1)
        .build();
  }
}
