package com.ismyrnov.messaging.rabbitmq.coltroller;

import com.ismyrnov.messaging.rabbitmq.service.task.first.Publisher;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class Controller {

  private final Publisher publisher;

  @PostMapping("/messaging/rabbitmq/publish")
  public void publish(@RequestBody String message) {
    publisher.publish(message);
  }
}
