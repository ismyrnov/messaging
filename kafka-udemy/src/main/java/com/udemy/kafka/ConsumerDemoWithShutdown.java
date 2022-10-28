package com.udemy.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class ConsumerDemoWithShutdown {
  private static final Logger log = LoggerFactory.getLogger(ConsumerDemoWithShutdown.class);

  public static void main(String[] args) {
    log.info("I am a Kafka Consumer");

    String bootstrapServers = "127.0.0.1:9092";
    String topic = "testTopic1";
    String groupId = "test-group-id-1";

    // create Consumer properties
    Properties properties = new Properties();
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    // create the consumer
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

    final Thread mainThread = Thread.currentThread();
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        log.info("Detected shutdown, let`s exit by calling consumer.wakeup()...");
        consumer.wakeup();
        try {
          mainThread.join();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    try {
      // subscribe consumer
      consumer.subscribe(List.of(topic));

      while (true) {
        log.info("Polling...");
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

        for (ConsumerRecord<String, String> record : records) {
          log.info("Key: " + record.key() + ", Value: " + record.value());
          log.info("Partition: " + record.partition() + ", Offset: " + record.offset());
        }
      }
    } catch (WakeupException e) {
      log.info("Wakeup exception!");
      // we ignore it
    } catch (Exception e) {
      log.error("Unexpected exception: {}", e.getMessage());
    } finally {
      consumer.close(); // this will commit offsets
    }
  }
}