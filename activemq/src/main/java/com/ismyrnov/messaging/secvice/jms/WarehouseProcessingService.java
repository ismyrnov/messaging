package com.ismyrnov.messaging.secvice.jms;

import com.ismyrnov.messaging.model.BookOrder;
import com.ismyrnov.messaging.model.ProcessedBookOrder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@Service
public class WarehouseProcessingService {
  public static final String BOOK_PROCESSED_QUEUE = "book.order.processed.queue";

  private final JmsTemplate jmsTemplate;

  @Transactional
  public void processOrder(BookOrder bookOrder){
    ProcessedBookOrder processedOrder = ProcessedBookOrder.builder()
        .bookOrder(bookOrder)
        .processingDateTime(new Date())
        .expectedShippingDateTime(new Date())
        .build();
    jmsTemplate.convertAndSend(BOOK_PROCESSED_QUEUE, processedOrder);
  }
}
