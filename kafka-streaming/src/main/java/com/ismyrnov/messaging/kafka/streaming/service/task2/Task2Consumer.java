package com.ismyrnov.messaging.kafka.streaming.service.task2;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.ismyrnov.messaging.kafka.streaming.congig.MessagingConfig.TASK2_LONG_TOPIC;
import static com.ismyrnov.messaging.kafka.streaming.congig.MessagingConfig.TASK2_SHORT_TOPIC;
import static com.ismyrnov.messaging.kafka.streaming.congig.MessagingConfig.TASK2_TOPIC;

@Slf4j
@Component
@AllArgsConstructor
public class Task2Consumer {

  @KafkaListener(topics = {TASK2_TOPIC}, groupId = "test-kafka-streams-id-1")
  public void consume(ConsumerRecord<String, String> record) {
   log.info("--Got a message '{}'", record.value());
  }

  @KafkaListener(topics = {TASK2_SHORT_TOPIC}, groupId = "test-kafka-streams-id-1")
  public void consumeShort(ConsumerRecord<String, String> record) {
    log.info("--Got short message '{}'", record.value());
  }

  @KafkaListener(topics = {TASK2_LONG_TOPIC}, groupId = "test-kafka-streams-id-1")
  public void consumeLong(ConsumerRecord<String, String> record) {
    log.info("--Got long message '{}'", record.value());
  }

}
