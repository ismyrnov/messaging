package com.ismyrnov.messaging.controller;

import com.ismyrnov.messaging.model.BookOrder;
import com.ismyrnov.messaging.secvice.jms.pubsub.PubSubSender;
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

  private final PubSubSender orderService;

  @PostMapping("/messaging/activemq")
  public void send(@RequestBody BookOrder order) {
    orderService.send(order);
  }

  @ExceptionHandler(value = { RuntimeException.class})
  protected ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
    return ResponseEntity.internalServerError().body(exception.getMessage());
  }

}
