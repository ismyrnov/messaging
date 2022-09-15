package com.ismyrnov.messaging.kafka.streaming.congig;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class MessagingConfig {

  public static final String TASK1_TOPIC1 = "task1Topic1";
  public static final String TASK1_TOPIC2 = "task1Topic2";

  @Bean
  public KStream<String, String> messagingStream1(StreamsBuilder streamsBuilder) {
    KStream<String, String> messagingStream = streamsBuilder.stream(TASK1_TOPIC1, Consumed.with(Serdes.String(), Serdes.String()));
    messagingStream.to(TASK1_TOPIC2, Produced.with(Serdes.String(), Serdes.String()));
    return messagingStream;
  }

  @Bean
  public KStream<String, String> messagingStream2(StreamsBuilder streamBuilder) {
    return streamBuilder.stream(TASK1_TOPIC2, Consumed.with(Serdes.String(), Serdes.String()));
  }

}
