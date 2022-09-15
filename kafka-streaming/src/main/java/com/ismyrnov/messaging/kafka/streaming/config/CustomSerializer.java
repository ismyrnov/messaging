package com.ismyrnov.messaging.kafka.streaming.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismyrnov.messaging.kafka.streaming.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class CustomSerializer implements Serializer<Employee> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Employee data) {
        try {
            if (Objects.isNull(data)) {
                log.warn("Empty data");
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException ex) {
            throw new SerializationException("Error occurred during deserializing: {}", ex);
        }
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Employee data) {
        return new byte[0];
    }

    @Override
    public void close() {

    }
}
