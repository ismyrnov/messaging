package com.ismyrnov.messaging.activemq.secvice.pubsub;

import com.ismyrnov.messaging.activemq.model.BookOrder;
import com.ismyrnov.messaging.activemq.secvice.MessagingProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DurableReceiver {

  @JmsListener(destination = MessagingProperties.PUB_SUB_TOPIC, containerFactory = "durableJmsContainerFactory", subscription = MessagingProperties.PUB_SUB_TOPIC)
  public void receive(@Payload BookOrder order) {
    log.info("Received PubSub durable message: {}", order);
  }
}
