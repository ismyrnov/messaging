package com.ismyrnov.messaging.secvice.jms.pubsub;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

public class PubSubProperties {

  public static final String BOOK_TOPIC = "new-books-order-topic";
}
