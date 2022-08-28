package com.ismyrnov.messaging.activemq.secvice.pubsub;

import com.ismyrnov.messaging.activemq.model.BookOrder;
import com.ismyrnov.messaging.activemq.secvice.Sender;
import com.ismyrnov.messaging.activemq.secvice.MessagingProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PubSubSender implements Sender {

  private final JmsTemplate topicJmsTemplate;

  public void send(BookOrder order) {
    log.info("Sending PubSub message: {}", order);
    topicJmsTemplate.convertAndSend(MessagingProperties.PUB_SUB_TOPIC, order);
  }

}
