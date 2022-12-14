package com.ismyrnov.messaging.kafka.service.first;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.ismyrnov.messaging.kafka.config.KafkaConfiguration.TOPIC;

@Slf4j
@Service
@AllArgsConstructor
public class FirstPublisher {

  private final KafkaTemplate<String, String> kafkaTemplate;

  public void publish(String message) {
    log.info("-- Publisher is sending message... {}", message);
    try {
      kafkaTemplate.send(TOPIC, message);
    } catch (KafkaException ex) {
      log.error("Publishing error: ", ex);
      return;
    }
    log.info("-- Successfully Publisher's sent the message");
  }

}
