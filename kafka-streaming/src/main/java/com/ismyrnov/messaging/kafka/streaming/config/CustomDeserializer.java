package com.ismyrnov.messaging.kafka.streaming.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismyrnov.messaging.kafka.streaming.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class CustomDeserializer implements Deserializer<Employee> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Employee deserialize(String topic, byte[] data) {
        try {
            if (Objects.isNull(data)) {
                log.warn("Empty data");
                return null;
            }
            return objectMapper.readValue(new String(data, Charset.defaultCharset()), Employee.class);
        } catch (Exception ex) {
            throw new SerializationException("Error occurred during deserializing: {}", ex);
        }
    }

    @Override
    public void close() {
    }
}
