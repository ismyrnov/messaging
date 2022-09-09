package com.ismyrnov.messaging.kafka.service.third;

import com.ismyrnov.messaging.kafka.model.Taxi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;

import static com.ismyrnov.messaging.kafka.config.KafkaConfiguration.TAXI_TOPIC;

@Slf4j
@Service
public class ThirdConsumer {

  private final AtomicInteger distance = new AtomicInteger(0);

  @Transactional(isolation = Isolation.READ_COMMITTED)
  @KafkaListener(id = "taxi-id-1", topics = TAXI_TOPIC)
  public void consume(Taxi message) {
    log.info("-Consumer's got a message: {}", message);

    synchronized (this) {
      log.info("-- Total distance: {}", distance.addAndGet(message.getEndPoint() - message.getStartPoint()));
    }
  }
}
