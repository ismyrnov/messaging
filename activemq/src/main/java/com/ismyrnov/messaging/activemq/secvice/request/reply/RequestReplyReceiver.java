package com.ismyrnov.messaging.activemq.secvice.request.reply;

import com.ismyrnov.messaging.activemq.secvice.MessagingProperties;
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

@Slf4j
@Service
@AllArgsConstructor
public class RequestReplyReceiver {

  private final JmsTemplate queueJmsTemplate;

  @SneakyThrows
  @JmsListener(destination = MessagingProperties.BOOK_TEMP_QUEUE, containerFactory = "defaultJmsContainerFactory")
  public void receive(Message message) {
    String text = ((TextMessage)message).getText();
    log.info("Receiver received RequestReply message from Request: {} ", text);
//    Thread.sleep(10000);
    queueJmsTemplate.send(message.getJMSReplyTo(), new MessageCreator() {
      @Override
      public Message createMessage(Session session) throws JMSException {
        Message responseMsg = session.createTextMessage(text + "-reply-1");
        responseMsg.setJMSCorrelationID(message.getJMSCorrelationID());
        return responseMsg;
      }
    });

  }
}
