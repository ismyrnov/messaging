package com.ismyrnov.messaging.kafka.service.third;

import com.ismyrnov.messaging.kafka.model.Taxi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static com.ismyrnov.messaging.kafka.config.KafkaConfiguration.TAXI_TOPIC;

@Slf4j
@Service
@AllArgsConstructor
public class ThirdPublisher {

  private final KafkaTemplate<String, Taxi> kafkaTemplate;

  @Transactional(isolation = Isolation.READ_COMMITTED)
  public void publish(Taxi message) {
    log.info("-- Publisher is sending message... {}", message);
    try {
      kafkaTemplate.send(TAXI_TOPIC, message);
    } catch (KafkaException ex) {
      log.error("Publishing error: ", ex);
      return;
    }
    log.info("-- Successfully Publisher's sent the message");
  }

}
