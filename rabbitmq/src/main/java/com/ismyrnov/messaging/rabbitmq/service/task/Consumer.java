package com.ismyrnov.messaging.rabbitmq.service.task;

import org.springframework.messaging.Message;

public interface Consumer {

  void processQueue1(Message<String> message);

  void processQueue2(Message<String> message);
}
