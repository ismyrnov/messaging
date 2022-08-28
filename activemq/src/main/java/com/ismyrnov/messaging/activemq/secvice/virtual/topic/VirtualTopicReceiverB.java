package com.ismyrnov.messaging.activemq.secvice.virtual.topic;

import com.ismyrnov.messaging.activemq.model.BookOrder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static com.ismyrnov.messaging.activemq.secvice.MessagingProperties.BOOK_VIRTUAL_TOPIC;

@Slf4j
@Service
@AllArgsConstructor
public class VirtualTopicReceiverB {
  public static final String CLIENT_B_ID = "clientB";
  private static final String BOOK_VIRTUAL_QUEUE = "Consumer."
      + CLIENT_B_ID
      + "."
      + BOOK_VIRTUAL_TOPIC;

  @JmsListener(destination = BOOK_VIRTUAL_QUEUE, containerFactory = "defaultJmsContainerFactory")
  public void receive(@Payload BookOrder order) {
    log.info("Received VirtTopic B message: {}", order);
  }
}
