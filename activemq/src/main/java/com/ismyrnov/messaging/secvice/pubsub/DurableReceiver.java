package com.ismyrnov.messaging.secvice.pubsub;

import com.ismyrnov.messaging.model.BookOrder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static com.ismyrnov.messaging.secvice.MessagingProperties.BOOK_TOPIC;

@Slf4j
@Service
@AllArgsConstructor
public class DurableReceiver {

  @JmsListener(destination = BOOK_TOPIC, containerFactory = "durableJmsContainerFactory", subscription = BOOK_TOPIC) // by default Transactional
//  @SendTo(PROCESSED_QUEUE)
  public void receive(@Payload BookOrder order
//      ,
//                                                         @Header("orderState") String orderState,
//                                                         @Header("bookOrderId") String bookOrderId,
//                                                         @Header("storeId") String storeId
  ) {
    log.info("Received PubSub durable message...");
    log.info("--Message: {} with "
//            + "storeId: {}, orderState: {}, bookOrderId: {}"
        , order);//, storeId, orderState, bookOrderId);

    if (order.getBook().getTitle().startsWith("L"))
      throw new IllegalArgumentException("Book '"+order.getBook().getId()+"' is not allowed");

  }
}
