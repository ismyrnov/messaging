package com.ismyrnov.messaging.secvice.jms;

import com.ismyrnov.messaging.model.BookOrder;

public interface Sender {

  void send(BookOrder order);
}
