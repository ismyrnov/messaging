package com.ismyrnov.messaging.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.ismyrnov.messaging.kafka.config.KafkaConfiguration.TOPIC;

@Slf4j
@Service
public class Consumer {

  @KafkaListener(id = "my-id-1", topics = TOPIC)
  public void consume(String message) {
    log.info("Consumer's got a message: {}", message);
    throw new RuntimeException("Error !!!");
  }
}
