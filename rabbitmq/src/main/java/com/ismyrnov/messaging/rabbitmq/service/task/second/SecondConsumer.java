package com.ismyrnov.messaging.rabbitmq.service.task.second;

import com.ismyrnov.messaging.rabbitmq.service.task.Consumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;
import java.util.Map;

import static com.ismyrnov.messaging.rabbitmq.service.MessageProperties.CONSUMER_1;
import static com.ismyrnov.messaging.rabbitmq.service.MessageProperties.FAILED_EXCHANGE;

@Slf4j
@AllArgsConstructor
public class SecondConsumer implements Consumer {

  private final StreamBridge streamBridge;

  @Override
  public void processQueue1(Message<String> message) {
    log.info("Consumer '{}' got a message: {} ...", CONSUMER_1, message.getPayload());

    var deathHeader = message.getHeaders().get("x-death", List.class);
    var death = deathHeader != null && deathHeader.size() > 0
        ? (Map<String, Object>) deathHeader.get(0)
        : null;
    if ( death != null && (long) death.get("count")   > 2) {
      log.error("Consumer '{}' Death Attempt reached message 'don't send to DLX': {}", CONSUMER_1, message.getPayload());
      throw new ImmediateAcknowledgeAmqpException("Failed after 3 attempts");
    }
    throw new AmqpRejectAndDontRequeueException("failed");
  }

  @Override
  public void processQueue2(Message<String> message) {
    String failedMessage = "Failed message: " + message.getPayload();
    log.info("Consumer '{}' publishing FAILED message to exchange: '{}', message: {}", CONSUMER_1, FAILED_EXCHANGE, failedMessage);
    streamBridge.send("failed-out-0",
        MessageBuilder
            .withPayload(failedMessage)
            .build());
    return;
  }
}
