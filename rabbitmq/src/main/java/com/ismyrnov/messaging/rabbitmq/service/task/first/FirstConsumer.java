package com.ismyrnov.messaging.rabbitmq.service.task.first;

import com.ismyrnov.messaging.rabbitmq.service.task.Consumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.atomic.AtomicInteger;

import static com.ismyrnov.messaging.rabbitmq.service.MessageProperties.CONSUMER_1;
import static com.ismyrnov.messaging.rabbitmq.service.MessageProperties.CONSUMER_2;
import static com.ismyrnov.messaging.rabbitmq.service.MessageProperties.FAILED_EXCHANGE;
import static com.ismyrnov.messaging.rabbitmq.service.task.first.MessageType.RETRY_MESSAGE;
import static com.ismyrnov.messaging.rabbitmq.service.task.first.MessageType.UNPROCESSABLE_MESSAGE;

@Slf4j
@AllArgsConstructor
public class FirstConsumer implements Consumer {

  /*private static final String DURABLE = "true";*/

  private final StreamBridge streamBridge;

/*  @RabbitListener(
      bindings = @QueueBinding(
          value = @Queue(name = QUEUE_1, durable = DURABLE),
          key = ROUTING_KEY_1,
          exchange = @Exchange(name = TOPIC_EXCHANGE, type = TOPIC)
      )
  )*/
/*  @StreamListener(QUEUE_1)
  public void listenQueue1(Message<String> message) {doListen(CONSUMER_1, message);}*/

  public void processQueue1(Message<String> message) {
    log.info("Consumer '{}' got a message: {} ...", CONSUMER_1, message.getPayload());
    if (((AtomicInteger) message.getHeaders().get("deliveryAttempt")).get() > 2) {
      log.error("Consumer '{}' Max Delivery Attempt reached message: {}", CONSUMER_1, message.getPayload());
      publishFailed(message.getPayload());
//      throw new ImmediateAcknowledgeAmqpException("Failed after 3 attempts");
    } else if (UNPROCESSABLE_MESSAGE.toString().equals(message.getPayload())) {
      log.error("Consumer '{}' Unprocessable message: {}", CONSUMER_1, message.getPayload());
      publishFailed(message.getPayload());
      return;
    } else if (RETRY_MESSAGE.toString().equals(message.getPayload())) {
      throw new RuntimeException("ERROR occurred: Consumer '" + CONSUMER_1 + "' re-try to process message: " + message.getPayload());
    }

    log.info("Consumer '{}' got message: '{}", CONSUMER_1, message.getPayload());
  }

  public void processQueue2(Message<String> message) {
    log.info("Consumer '{}' got a message...", CONSUMER_2);
    log.info("Consumer '{}' processed message: '{}", CONSUMER_2, message.getPayload());
  }

  private void publishFailed(String payload) {
    String failedMessage = "Failed message: " + payload;
    log.info("Consumer '{}' publishing FAILED message to exchange: '{}', message: {}", CONSUMER_1, FAILED_EXCHANGE, failedMessage);
    streamBridge.send("failed-out-0",
        MessageBuilder
            .withPayload(failedMessage)
            .build());
    return;
  }

/*  @RabbitListener(
      bindings = @QueueBinding(
          value = @Queue(name = QUEUE_2, durable = DURABLE),
          key = ROUTING_KEY_2,
          exchange = @Exchange(name = TOPIC_EXCHANGE, type = TOPIC)
      )
  )*/
/*  @StreamListener(QUEUE_2)
  public void listenQueue2(Message<String> message) {doListen(CONSUMER_2, message);}

  private void doListen(String consumerName, Message<String> message) {
    log.info("Consumer '{}' got message: '{}' with headers: {}", consumerName, message.getPayload(), message.getHeaders());
  }*/
}
