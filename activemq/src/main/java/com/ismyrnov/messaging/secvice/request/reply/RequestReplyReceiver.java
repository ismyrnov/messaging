package com.ismyrnov.messaging.secvice.request.reply;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
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
public class RequestReplyReceiver {

  private final JmsTemplate jmsTemplate;

  @SneakyThrows
  @JmsListener(destination = BOOK_TEMP_QUEUE) // by default Transactional
  public void receive(Message message) {
    log.info("Received RequestReply message...");
    String text = ((TextMessage)message).getText();
    log.info("--Message received for Request: {} ", text);
    Thread.sleep(10000);
    jmsTemplate.send(message.getJMSReplyTo(), new MessageCreator() {
      @Override
      public Message createMessage(Session session) throws JMSException {
        Message responseMsg = session.createTextMessage(text + "-reply-1");
        responseMsg.setJMSCorrelationID(message.getJMSCorrelationID());
        return responseMsg;
      }
    });

  }
}
