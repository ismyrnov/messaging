package com.ismyrnov.messaging.activemq.listener;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Slf4j
@Component
public class BookOrderProcessingMessageListener implements MessageListener {

  @SneakyThrows
  @Override
  public void onMessage(Message message) {
    String text = ((TextMessage) message).getText();
    log.info("Message text: {}", text);
  }
}
