package com.udemy.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class ConsumerDemo {
  private static final Logger log = LoggerFactory.getLogger(ConsumerDemo.class);

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
  }
}