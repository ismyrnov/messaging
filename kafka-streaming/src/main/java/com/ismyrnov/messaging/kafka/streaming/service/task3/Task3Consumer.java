package com.ismyrnov.messaging.kafka.streaming.service.task3;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.ismyrnov.messaging.kafka.streaming.config.MessagingConfig.TASK3_TOPIC1;
import static com.ismyrnov.messaging.kafka.streaming.config.MessagingConfig.TASK3_TOPIC2;

@Slf4j
@Component
@AllArgsConstructor
public class Task3Consumer {

  private final KStream<Long, String> messagingTask3JoinStream;

  @KafkaListener(topics = {TASK3_TOPIC1}, groupId = "test-kafka-streams-id-1")
  public void consumeTopic1(ConsumerRecord<String, String> record) {
    log.info("--Got a message '{}' from topic {}", record.value(), TASK3_TOPIC1);
//    messagingTask3JoinStream.peek((key, value) -> log.info("Joined key: {} value: {}", key, value));
  }

  @KafkaListener(topics = {TASK3_TOPIC2}, groupId = "test-kafka-streams-id-1")
  public void consumeTopic2(ConsumerRecord<String, String> record) {
    log.info("--Got a message '{}' from topic {}", record.value(), TASK3_TOPIC2);
//    messagingTask3JoinStream.peek((key, value) -> log.info("Joined key: {} value: {}", key, value));
  }
}
