package com.ismyrnov.messaging.controller;

import com.ismyrnov.messaging.model.BookOrder;
import com.ismyrnov.messaging.secvice.jms.BookStoreOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@AllArgsConstructor
public class BookController {

  private final BookStoreOrderService orderService;

  @PostMapping("/messaging/activemq/{storeId}/{orderId}")
  public void send(@RequestBody BookOrder order,
                   @PathVariable("storeId") String storeId,
                   @PathVariable("orderId") String orderId) {
    orderService.send(order, storeId, orderId);
  }

  @ExceptionHandler(value = { RuntimeException.class})
  protected ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
    return ResponseEntity.internalServerError().body(exception.getMessage());
  }

}
