package com.ismyrnov.messaging.rabbitmq.service.task.third;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

@Slf4j
@RequiredArgsConstructor
public class DeadletterConsumer implements java.util.function.Consumer<Message<String>> {

  public void accept(Message<String> message) {
    log.info("!!! DEADLETTER: Consumer 'DeadletterConsumer' got a message: {} ...", message.getPayload());
  }
}
