package com.ismyrnov.messaging.secvice.request.reply;

import com.ismyrnov.messaging.model.BookOrder;
import com.ismyrnov.messaging.secvice.Sender;
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

import static com.ismyrnov.messaging.secvice.MessagingProperties.BOOK_TEMP_QUEUE;

@Slf4j
@Service
@AllArgsConstructor
public class RequestReplySender implements Sender {

  private final JmsTemplate jmsTemplate;

  @SneakyThrows
  public void send(BookOrder order) {
    log.info("Sending message...");
    jmsTemplate.setReceiveTimeout(20000);
    TextMessage received = (TextMessage)jmsTemplate.sendAndReceive(BOOK_TEMP_QUEUE, new MessageCreator() {
      @Override
      public Message createMessage(Session session) throws JMSException {
        log.info("--Message sending: {} ", order.getId());
        TextMessage message = session.createTextMessage(order.getId());
        message.setJMSCorrelationID("foo-id");
        return message;
      }
    });
    log.info("Sender got message...");
    log.info("--Message received for Response: {}", received.getText());
  }

}
