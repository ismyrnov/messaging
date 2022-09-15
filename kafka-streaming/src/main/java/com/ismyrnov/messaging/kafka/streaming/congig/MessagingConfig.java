package com.ismyrnov.messaging.kafka.streaming.congig;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableKafka
@EnableKafkaStreams
public class MessagingConfig {

  public static final String TASK1_TOPIC1 = "task1Topic1";
  public static final String TASK1_TOPIC2 = "task1Topic2";
  public static final String TASK2_TOPIC = "task2Topic";
  public static final String TASK2_SHORT_TOPIC = "task2ShortTopic";
  public static final String TASK2_LONG_TOPIC = "task2LongTopic";
  public static final String TASK3_TOPIC1 = "task3Topic1";
  public static final String TASK3_TOPIC2 = "task3Topic2";

  @Bean
  public KStream<String, String> messagingTask1Stream1(StreamsBuilder streamsBuilder) {
    KStream<String, String> messagingStream = streamsBuilder.stream(TASK1_TOPIC1, Consumed.with(Serdes.String(), Serdes.String()));
    messagingStream.to(TASK1_TOPIC2, Produced.with(Serdes.String(), Serdes.String()));
    return messagingStream;
  }

  @Bean
  public KStream<String, String> messagingTask1Stream2(StreamsBuilder streamBuilder) {
    return streamBuilder.stream(TASK1_TOPIC2, Consumed.with(Serdes.String(), Serdes.String()));
  }

  @Bean
  public KStream<String, String> messagingTask2Stream1(StreamsBuilder streamBuilder) {
    KStream<String, String> messagingStream = streamBuilder.stream(TASK2_TOPIC, Consumed.with(Serdes.String(), Serdes.String()));
    Consumer<KStream<Integer, String>> shortTopic = stream -> stream.to(TASK2_SHORT_TOPIC, Produced.with(Serdes.Integer(), Serdes.String()));
    Consumer<KStream<Integer, String>> longTopic = stream -> stream.to(TASK2_LONG_TOPIC, Produced.with(Serdes.Integer(), Serdes.String()));
    messagingStream
        .map((key, value) -> new KeyValue<>(value.length(), value))
        .peek((key, value) -> log.info("New key-value pairs: {}-{}", key, value))
        .flatMap((key, value) -> Arrays.stream(value.split(" "))
            .map(word -> new KeyValue<Integer, String>(word.length(), word))
            .collect(Collectors.toList()))
        .split(Named.as("words-"))
        .branch((key, value) -> value.length() < 10, Branched.withConsumer(shortTopic).withName("short"))
        .branch((key, value) -> value.length() >= 10, Branched.withConsumer(longTopic).withName("long"))
        .noDefaultBranch();

    return messagingStream;
  }

  @Bean
  public KStream<String, String> messagingTask2ShortStream(StreamsBuilder streamBuilder) {
    return streamBuilder.stream(TASK2_SHORT_TOPIC, Consumed.with(Serdes.String(), Serdes.String()));
  }

  @Bean
  public KStream<String, String> messagingTask2LongStream(StreamsBuilder streamBuilder) {
    return streamBuilder.stream(TASK2_LONG_TOPIC, Consumed.with(Serdes.String(), Serdes.String()));
  }

  @Bean
  public KStream<Long, String> messagingTask3JoinStream(StreamsBuilder streamBuilder) {
    KStream<Long, String> stream1 = filteredStream(streamBuilder, TASK3_TOPIC1);
    KStream<Long, String> stream2 = filteredStream(streamBuilder, TASK3_TOPIC2);

    return stream1.selectKey((key, value) -> key)
        .join(stream2.selectKey((key, value) -> key), (value1, value2) -> value1 + "_" + value2,
            JoinWindows.of(Duration.ofSeconds(3)),
            StreamJoined.with(Serdes.Long(), Serdes.String(), Serdes.String()))
        .peek((key, value) -> log.info("Joined key: {} value: {}", key, value));
  }

  @Bean
  public KStream<Long, String> messagingTask3Stream1(StreamsBuilder streamBuilder) {
    return streamBuilder.stream(TASK3_TOPIC1, Consumed.with(Serdes.Long(), Serdes.String()));
  }

  @Bean
  public KStream<Long, String> messagingTask3Stream2(StreamsBuilder streamBuilder) {
    return streamBuilder.stream(TASK3_TOPIC2, Consumed.with(Serdes.Long(), Serdes.String()));
  }

  private KStream<Long, String> filteredStream(StreamsBuilder streamBuilder, String topic) {
    return streamBuilder.stream(topic, Consumed.with(Serdes.Long(), Serdes.String()))
        .filter((key, value) -> value.contains(":"))
        .map((key, value) -> createKeyValue(value));
  }

  private KeyValue<Long, String> createKeyValue(String origin) {
    String[] splited = origin.split(":");
    return new KeyValue<>(Long.valueOf(splited[0]), splited[1]);
  }
}
