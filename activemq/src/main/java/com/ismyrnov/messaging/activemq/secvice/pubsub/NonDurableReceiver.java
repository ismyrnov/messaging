package com.ismyrnov.messaging.activemq.secvice.pubsub;

import com.ismyrnov.messaging.activemq.model.BookOrder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static com.ismyrnov.messaging.activemq.secvice.MessagingProperties.PUB_SUB_TOPIC;

@Slf4j
@Service
@AllArgsConstructor
public class NonDurableReceiver {

  @JmsListener(destination = PUB_SUB_TOPIC, containerFactory = "nonDurableJmsContainerFactory")
  public void receive(@Payload BookOrder order) {
    log.info("Received PubSub non-durable message: {} ", order);
  }
}
