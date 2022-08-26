package com.ismyrnov.messaging.controller;

import com.ismyrnov.messaging.model.BookOrder;
import com.ismyrnov.messaging.secvice.pubsub.PubSubSender;
import com.ismyrnov.messaging.secvice.request.reply.RequestReplySender;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@AllArgsConstructor
public class BookController {

  private final PubSubSender pubSubSender;

  private final RequestReplySender requestReplySender;

  @PostMapping("/messaging/activemq/pubsub")
  public void send(@RequestBody BookOrder order) {
    pubSubSender.send(order);
  }

  @PostMapping("/messaging/activemq/requestreply")
  public void sendRequestReply(@RequestBody BookOrder order) {
    requestReplySender.send(order);
  }

  @ExceptionHandler(value = { RuntimeException.class})
  protected ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
    return ResponseEntity.internalServerError().body(exception.getMessage());
  }

}
