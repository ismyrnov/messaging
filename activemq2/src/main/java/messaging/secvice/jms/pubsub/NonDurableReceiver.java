package messaging.secvice.jms.pubsub;

import com.ismyrnov.messaging.model.BookOrder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static messaging.secvice.jms.pubsub.PubSubProperties.BOOK_TOPIC;

@Slf4j
@Service
@AllArgsConstructor
public class NonDurableReceiver {

  @JmsListener(destination = BOOK_TOPIC, containerFactory = "nonDurableJmsContainerFactory")
  public void receive(@Payload BookOrder order) {
    log.info("Received PubSub non-durable message...");
    log.info("--Message: {} ", order);
  }
}
