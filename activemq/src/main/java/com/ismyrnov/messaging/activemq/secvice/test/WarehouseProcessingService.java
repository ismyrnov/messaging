package com.ismyrnov.messaging.activemq.secvice.test;

import com.ismyrnov.messaging.activemq.model.BookOrder;
import com.ismyrnov.messaging.activemq.model.ProcessedBookOrder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.adapter.JmsResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@AllArgsConstructor
//@Service
public class WarehouseProcessingService {
  public static final String PROCESSED_QUEUE = "book.order.processed.queue";
  private static final String CANCELED_QUEUE = "book.order.canceled.queue";

  private final JmsTemplate jmsTemplate;

  @Transactional
  public JmsResponse<Message<ProcessedBookOrder>>  processOrder(BookOrder bookOrder, String orderState, String storeId){

    Message<ProcessedBookOrder> message;

    if("NEW".equalsIgnoreCase(orderState)){
      message = add(bookOrder, storeId);
      return JmsResponse.forQueue(message, PROCESSED_QUEUE);
    } else if("UPDATE".equalsIgnoreCase(orderState)){
      message = update(bookOrder, storeId);
      return JmsResponse.forQueue(message, PROCESSED_QUEUE);
    } else if("DELETE".equalsIgnoreCase(orderState)){
      message = delete(bookOrder,storeId);
      return JmsResponse.forQueue(message, CANCELED_QUEUE);
    } else{
      throw new IllegalArgumentException("WarehouseProcessingService.processOrder(...) - orderState does not match expected criteria!");
    }

//    jmsTemplate.convertAndSend(PROCESSED_QUEUE, processedOrder);
  }

  private Message<ProcessedBookOrder> add(BookOrder bookOrder, String storeId){
    log.info("ADDING A NEW ORDER TO DB");
    return build(ProcessedBookOrder.builder()
        .bookOrder(bookOrder)
        .processingDateTime(new Date())
        .expectedShippingDateTime(new Date())
        .build(),
        "ADDED",
        storeId);
  }

  private Message<ProcessedBookOrder> update(BookOrder bookOrder, String storeId) {
    log.info("UPDATING A ORDER TO DB");
    return build(ProcessedBookOrder.builder()
        .bookOrder(bookOrder)
        .processingDateTime(new Date())
        .expectedShippingDateTime(new Date())
        .build(),
        "UPDATED",
        storeId);
  }

  private Message<ProcessedBookOrder> delete(BookOrder bookOrder, String storeId) {
    log.info("DELETING ORDER FROM DB");
    return build(ProcessedBookOrder.builder()
        .bookOrder(bookOrder)
        .processingDateTime(new Date())
        .build(),
        "DELETED",
        storeId);
  }

  private Message<ProcessedBookOrder> build(ProcessedBookOrder bookOrder, String orderState, String storeId){
    return MessageBuilder
        .withPayload(bookOrder)
        .setHeader("orderState", orderState)
        .setHeader("storeId", storeId)
        .build();
  }

}
