package com.ismyrnov.messaging.activemq.secvice;

import com.ismyrnov.messaging.activemq.model.BookOrder;

public interface Sender {

  void send(BookOrder order);
}
