package com.ismyrnov.messaging.kafka.streaming.service.task1;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.ismyrnov.messaging.kafka.streaming.config.MessagingConfig.TASK1_TOPIC2;

@Slf4j
@Component
@AllArgsConstructor
public class Task1Consumer {

  @KafkaListener(topics = {TASK1_TOPIC2}, groupId = "test-kafka-streams-id-1")
  public void consume(ConsumerRecord<String, String> record) {
   log.info("--Got redirected message '{}'", record.value());
  }

}
