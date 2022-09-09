package com.ismyrnov.messaging.kafka.service.second;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static com.ismyrnov.messaging.kafka.config.KafkaConfiguration.TOPIC;

@Slf4j
@Service
public class SecondConsumer {

  @Transactional(isolation = Isolation.READ_COMMITTED)
  @KafkaListener(id = "my-id-1", topics = TOPIC)
  public void consume(String message) {
    log.info("-Consumer's got a message: {}", message);
    throw new RuntimeException("Error !!!");
  }
}
