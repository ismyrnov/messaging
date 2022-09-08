package com.ismyrnov.messaging.kafka.controller;

import com.ismyrnov.messaging.kafka.service.Publisher;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class Controller {

  private final Publisher publisher;

  @PostMapping("/messaging/kafka")
  public void publish(@RequestBody String message) {
    publisher.publish(message);
  }
}
