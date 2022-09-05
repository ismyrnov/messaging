package com.ismyrnov.messaging.rabbitmq.config;

import com.ismyrnov.messaging.rabbitmq.service.task.third.DeadletterConsumer;
import com.ismyrnov.messaging.rabbitmq.service.task.third.ThirdConsumer;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;

@Profile("third")
@Configuration
public class ThirdMessagingConfig {

  @Bean
  public java.util.function.Consumer<Message<String>> deadletterSink() {
    return new DeadletterConsumer();
  }

  @Bean
  public java.util.function.Consumer<Message<String>>  queue1Sink(StreamBridge streamBridge) {
    return new ThirdConsumer(streamBridge);
  }

}
