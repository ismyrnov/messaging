package com.ismyrnov.messaging.secvice.jms;

import com.ismyrnov.messaging.model.BookOrder;
import com.ismyrnov.messaging.model.ProcessedBookOrder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.adapter.JmsResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import static com.ismyrnov.messaging.secvice.jms.BookStoreOrderService.BOOK_QUEUE;
import static com.ismyrnov.messaging.secvice.jms.WarehouseProcessingService.PROCESSED_QUEUE;

@Slf4j
@AllArgsConstructor
//@Service
public class WarehouseReceiver {

  private final WarehouseProcessingService receiverService;

  @JmsListener(destination = BOOK_QUEUE) // by default Transactional
//  @SendTo(PROCESSED_QUEUE)
  public JmsResponse<Message<ProcessedBookOrder>> receive(@Payload BookOrder order,
                                                         @Header("orderState") String orderState,
                                                         @Header("bookOrderId") String bookOrderId,
                                                         @Header("storeId") String storeId) {
    log.info("Received a message...");
    log.info("--Message: {} with storeId: {}, orderState: {}, bookOrderId: {}", order, storeId, orderState, bookOrderId);

    if (order.getBook().getTitle().startsWith("L"))
      throw new IllegalArgumentException("Book '"+order.getBook().getId()+"' is not allowed");

    return receiverService.processOrder(order, orderState, storeId);
  }
}
