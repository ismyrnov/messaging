package com.ismyrnov.messaging.rabbitmq.config;

import com.ismyrnov.messaging.rabbitmq.service.task.Consumer;
import com.ismyrnov.messaging.rabbitmq.service.task.first.FirstConsumer;
import com.ismyrnov.messaging.rabbitmq.service.task.first.FailedConsumer;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;

@Profile("first")
@Configuration
public class FirstMessagingConfig {

  @Bean
  public Consumer consumer(StreamBridge streamBridge) {
    return new FirstConsumer(streamBridge);
  }

}
