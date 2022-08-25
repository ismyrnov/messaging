package com.ismyrnov.messaging.secvice.jms;

import com.ismyrnov.messaging.model.BookOrder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;

@Slf4j
@AllArgsConstructor
//@Service
public class BookStoreOrderService {
  public static final String BOOK_QUEUE = "book.order.queue";

  private final JmsTemplate jmsTemplate;

  @Transactional
  public void send(BookOrder order, String storeId, String orderState) {
    log.info("Sending message...");
    log.info("--Message: {} with storeId: {}, orderState: {}", order, storeId, orderState);
    jmsTemplate.convertAndSend(BOOK_QUEUE, order, new MessagePostProcessor() {
      @Override
      public Message postProcessMessage(Message message) throws JMSException {
        message.setStringProperty("bookOrderId", order.getBook().getId());
        message.setStringProperty("storeId", storeId);
        message.setStringProperty("orderState", orderState);
        return message;
      }
    });
  }

}
