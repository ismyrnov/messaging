package com.ismyrnov.messaging.rabbitmq.service.task.first;

import com.ismyrnov.messaging.rabbitmq.model.MessageEntity;
import com.ismyrnov.messaging.rabbitmq.repository.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class FailedConsumer {

  private final MessageRepository repository;

  private static final String FAILED_CONSUMER = "failed-consumer-1";

  private final StreamBridge streamBridge;

  public void processFailedQueue(Message<String> message) {
    log.info("Consumer '{}' got a message...", FAILED_CONSUMER);
    repository.save(map(message.getPayload()));
    log.info("Consumer '{}' processed message: '{}", FAILED_CONSUMER, message.getPayload());
  }

  private MessageEntity map(String message) {
    return MessageEntity.builder()
        .message(message)
        .build();
  }

}
