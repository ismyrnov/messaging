package messaging.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.activemq")
public class JmsProperties {

//  @Value("${spring.activemq.brokerUrl}")
  private String brokerUrl;

//  @Value("${spring.activemq:}")
  private String user;

//  @Value("${spring.activemq:}")
  private String password;
}
