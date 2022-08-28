package com.ismyrnov.messaging.activemq.secvice.request.reply;

import com.ismyrnov.messaging.activemq.model.BookOrder;
import com.ismyrnov.messaging.activemq.secvice.Sender;
import com.ismyrnov.messaging.activemq.secvice.MessagingProperties;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Slf4j
@Service
@AllArgsConstructor
public class RequestReplySender implements Sender {

  private final JmsTemplate queueJmsTemplate;

  @SneakyThrows
  public void send(BookOrder order) {
    log.info("Sender sending message...");
    queueJmsTemplate.setReceiveTimeout(20000);
    TextMessage received = (TextMessage)queueJmsTemplate.sendAndReceive(MessagingProperties.BOOK_TEMP_QUEUE, new MessageCreator() {
      @Override
      public Message createMessage(Session session) throws JMSException {
        log.info("--Message sending: {} ", order.getId());
        TextMessage message = session.createTextMessage(order.getId());
        message.setJMSCorrelationID("foo-id");
        return message;
      }
    });
    log.info("Sender received message from Reply: {}", received.getText());
  }

}
