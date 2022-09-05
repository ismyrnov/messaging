package com.ismyrnov.messaging.rabbitmq.config;

import com.ismyrnov.messaging.rabbitmq.repository.MessageRepository;
import com.ismyrnov.messaging.rabbitmq.service.task.first.FailedConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;


@Configuration
public class MessagingConfig {

//  @Bean
//  public java.util.function.Consumer<Message<String>> queue2Sink() {
//    return new ;
//  }

  @Bean
  public java.util.function.Consumer<Message<String>> failedSink(MessageRepository repository) {
    return new FailedConsumer(repository);
  }
}
