package com.ismyrnov.messaging.rabbitmq.config;

import com.ismyrnov.messaging.rabbitmq.service.task.Consumer;
import com.ismyrnov.messaging.rabbitmq.service.task.first.FailedConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

@Configuration
public class MessagingConfig {

  @Bean
  public java.util.function.Consumer<Message<String>> queue1Sink(Consumer consumer) {
    return consumer::processQueue1;
  }

  @Bean
  public java.util.function.Consumer<Message<String>> queue2Sink(Consumer consumer) {
    return consumer::processQueue2;
  }

  @Bean
  public java.util.function.Consumer<Message<String>> failedSink(FailedConsumer firstConsumer) {
    return firstConsumer::processFailedQueue;
  }
}
