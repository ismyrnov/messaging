package com.ismyrnov.messaging.rabbitmq.service.task.first;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static com.ismyrnov.messaging.rabbitmq.service.MessageProperties.ROUTING_KEY_1;
import static com.ismyrnov.messaging.rabbitmq.service.MessageProperties.ROUTING_KEY_HEADER;
import static com.ismyrnov.messaging.rabbitmq.service.MessageProperties.TOPIC_EXCHANGE;

@Slf4j
@Service
@AllArgsConstructor
public class Publisher {
/*  private final AmqpTemplate amqpTemplate;*/
/*  private final Source source;*/
  private final StreamBridge streamBridge;

  public void publish(String message) {
    String message1 = "Hello queue 1";
    log.info("Publishing to exchange: '{}', by routing key: '{}' message: {}", TOPIC_EXCHANGE, ROUTING_KEY_1, message1);
/*    amqpTemplate.convertAndSend(TOPIC_EXCHANGE, ROUTING_KEY_1, message1);*/
/*    source.output().send(MessageBuilder
        .withPayload(message1)
        .setHeader(ROUTING_KEY_HEADER, ROUTING_KEY_1)
        .build());*/
    streamBridge.send("source-out-0",
        MessageBuilder
            .withPayload(message)
            .setHeader(ROUTING_KEY_HEADER, ROUTING_KEY_1)
            .build());

//    streamBridge.send("source-out-0",
//        MessageBuilder
//            .withPayload(UNPROCESSABLE_MESSAGE)
//            .setHeader(ROUTING_KEY_HEADER, ROUTING_KEY_1)
//            .build());

//    String message2 = "Hello queue 2";
//    log.info("Publishing to exchange: '{}', by routing key: '{}' message: {}", TOPIC_EXCHANGE, ROUTING_KEY_2, message2);
/*    amqpTemplate.convertAndSend(TOPIC_EXCHANGE, ROUTING_KEY_2, message2);*/
/*    source.output().send(MessageBuilder
        .withPayload(message2)
        .setHeader(ROUTING_KEY_HEADER, ROUTING_KEY_2)
        .build());*/
//    streamBridge.send("source-out-0",
//        MessageBuilder
//            .withPayload(message2)
//            .setHeader(ROUTING_KEY_HEADER, ROUTING_KEY_2)
//            .build());
  }
}
