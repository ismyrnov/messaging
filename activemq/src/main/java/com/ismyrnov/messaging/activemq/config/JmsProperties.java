package com.ismyrnov.messaging.activemq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.activemq")
public class JmsProperties {
  private String brokerUrl;
  private String user;
  private String password;
}
