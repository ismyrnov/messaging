package com.ismyrnov.messaging.kafka.streaming.service.task4;

import com.ismyrnov.messaging.kafka.streaming.model.Employee;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.ismyrnov.messaging.kafka.streaming.config.MessagingConfig.TASK4_TOPIC;

@Slf4j
@Component
@AllArgsConstructor
public class Task4Consumer {

  @KafkaListener(topics = {TASK4_TOPIC}, groupId = "test-kafka-streams-id-1")
  public void consumeTopic4(ConsumerRecord<String, Employee> record) {
    log.info("--Got a message '{}' from topic {}", record.value(), TASK4_TOPIC);
  }

}
