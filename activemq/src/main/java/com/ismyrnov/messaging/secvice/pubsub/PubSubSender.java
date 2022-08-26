package com.ismyrnov.messaging.secvice.pubsub;

import com.ismyrnov.messaging.model.BookOrder;
import com.ismyrnov.messaging.secvice.Sender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import static com.ismyrnov.messaging.secvice.MessagingProperties.BOOK_TOPIC;

@Slf4j
@Service
@AllArgsConstructor
public class PubSubSender implements Sender {

  private final JmsTemplate jmsTemplate;

  public void send(BookOrder order) {
    log.info("Sending PubSub message...");
    log.info("--Message: {} ", order);
    jmsTemplate.convertAndSend(BOOK_TOPIC, order);
  }

}
