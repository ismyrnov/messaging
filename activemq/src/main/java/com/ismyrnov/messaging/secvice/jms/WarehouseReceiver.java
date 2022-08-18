package com.ismyrnov.messaging.secvice.jms;

import com.ismyrnov.messaging.model.BookOrder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import static com.ismyrnov.messaging.secvice.jms.BookStoreOrderService.BOOK_QUEUE;

@Slf4j
@AllArgsConstructor
@Service
public class WarehouseReceiver {

  private final WarehouseProcessingService receiverService;

  @JmsListener(destination = BOOK_QUEUE) // by default Transactional
  public void receive(BookOrder order) {
    log.info("Received a message...");
    log.info("--Message: {}", order);

//    if (order.getBook().getTitle().startsWith("L"))
//      throw new RuntimeException("Book '"+order.getBook().getId()+"' is not allowed");

    receiverService.processOrder(order);
  }
}
