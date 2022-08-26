package com.ismyrnov.messaging.secvice;

import com.ismyrnov.messaging.model.BookOrder;

import javax.jms.JMSException;

public interface Sender {

  void send(BookOrder order) throws JMSException;
}
