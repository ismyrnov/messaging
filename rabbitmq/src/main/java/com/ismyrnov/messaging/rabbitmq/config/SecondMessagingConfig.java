package com.ismyrnov.messaging.rabbitmq.config;

import com.ismyrnov.messaging.rabbitmq.service.task.Consumer;
import com.ismyrnov.messaging.rabbitmq.service.task.second.SecondConsumer;
import org.springframework.amqp.core.DeclarableCustomizer;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("second")
@Configuration
public class SecondMessagingConfig {

  @Bean
  @ConditionalOnProperty(name = "republish", havingValue = "true")
  public DeclarableCustomizer declarableCustomizer() {
    return declarable -> {
      if (declarable instanceof Queue) {
        var queue = (Queue) declarable;
        if (queue.getName().equals("queue-1")
            || queue.getName().equals("queue-2")) {
          queue.removeArgument("x-dead-letter-exchange");
          queue.removeArgument("x-dead-letter-routing-key");
        }
      }
      return declarable;
    };
  }

  @Bean
  public Consumer consumer(StreamBridge streamBridge) {
    return new SecondConsumer(streamBridge);
  }
}
