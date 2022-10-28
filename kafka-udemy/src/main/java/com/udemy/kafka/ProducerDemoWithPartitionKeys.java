package com.udemy.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithPartitionKeys {
  private static final Logger log = LoggerFactory.getLogger(ProducerDemoWithPartitionKeys.class);

  public static void main(String[] args) throws InterruptedException {
    log.info("I am a Kafka Producer");

    String bootstrapServers = "127.0.0.1:9092";

    // create Producer properties
    // used by default the DefaulTpartitioner with Sticky partition - batching quick set of messages - multiple messages are sent as a batch
    Properties properties = new Properties();
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    // create the producer
    KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

    for (int i=0; i <10; i++) {

      String value = "hello world " + i;
      String key = "id_" + i;
      // create a producer record
      ProducerRecord<String, String> producerRecord = new ProducerRecord<>("testTopic1", key, value);
      // send data - asynchronous
      producer.send(producerRecord, new Callback() {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
          // executes every time a record is successfully sent or an exception is thrown
          if (e == null) {
            // the record was successfully sent
            log.info("Received new metadata. \n" +
                "Topic:" + recordMetadata.topic() + "\n" +
                "Key: " + producerRecord.key() + "\n" +
                "Partition: " + recordMetadata.partition() + "\n" +
                "Offset: " + recordMetadata.offset() + "\n" +
                "Timestamp: " + recordMetadata.timestamp());
          } else {
            log.error("Error while producing", e);
          }
        }
      });

      Thread.sleep(1000);
    }

    // flush data - synchronous
    producer.flush();

    // flush and close producer
    producer.close();
  }
}