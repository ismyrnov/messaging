package com.ismyrnov.messaging.activemq.secvice.virtual.topic;

import com.ismyrnov.messaging.activemq.model.BookOrder;
import com.ismyrnov.messaging.activemq.secvice.MessagingProperties;
import com.ismyrnov.messaging.activemq.secvice.Sender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class VirtualTopicSender implements Sender {

  private final JmsTemplate topicJmsTemplate;

  @Override
  public void send(BookOrder order) {
    log.info("Sending Virt topic message: {} ", order);
    topicJmsTemplate.convertAndSend(new ActiveMQTopic(MessagingProperties.BOOK_VIRTUAL_TOPIC), order);
  }
}
