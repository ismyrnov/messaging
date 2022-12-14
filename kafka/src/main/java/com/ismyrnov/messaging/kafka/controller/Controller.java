package com.ismyrnov.messaging.kafka.controller;

import com.ismyrnov.messaging.kafka.model.Taxi;
import com.ismyrnov.messaging.kafka.service.first.FirstPublisher;
import com.ismyrnov.messaging.kafka.service.second.SecondPublisher;
import com.ismyrnov.messaging.kafka.service.third.ThirdPublisher;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class Controller {

  private final FirstPublisher firstPublisher;

  private final SecondPublisher secondPublisher;

  private final ThirdPublisher thirdPublisher;

  @PostMapping("/messaging/kafka/first")
  public void publishFirst(@RequestBody String message) {
    firstPublisher.publish(message);
  }

  @PostMapping("/messaging/kafka/second")
  public void publishSecond(@RequestBody String message) {
    secondPublisher.publish(message);
  }

  @PostMapping("/messaging/kafka/third")
  public void publishThird(@RequestBody Taxi message) {
    thirdPublisher.publish(message);
  }
}
