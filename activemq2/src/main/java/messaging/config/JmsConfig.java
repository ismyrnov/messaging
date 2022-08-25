package messaging.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableTransactionManagement
@EnableJms
@ConditionalOnProperty(name = "spring.activemq.enable", havingValue = "true")
@Configuration
@EnableConfigurationProperties(JmsProperties.class)
public class JmsConfig {

  @Bean
  public MessageConverter jacksonJmsConvertor() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }

  @Bean
  public SingleConnectionFactory activeMqConnectionFactory(JmsProperties jmsProperties) {
    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(jmsProperties.getUser(), jmsProperties.getPassword(), jmsProperties.getBrokerUrl());
    SingleConnectionFactory connectionFactory = new SingleConnectionFactory(factory);
    connectionFactory.setReconnectOnException(true); // CachingConnectionFactory by default set true
    connectionFactory.setClientId("3-non-durable-subscription-client-id");
    return connectionFactory;
  }

  @Bean
  public DefaultJmsListenerContainerFactory nonDurableJmsContainerFactory(JmsProperties jmsProperties, SingleConnectionFactory connectionFactory) {
    DefaultJmsListenerContainerFactory defaultJmsContainerFactory = new DefaultJmsListenerContainerFactory();
    defaultJmsContainerFactory.setConnectionFactory(connectionFactory);
    defaultJmsContainerFactory.setSubscriptionDurable(true);
    defaultJmsContainerFactory.setPubSubDomain(true);
    defaultJmsContainerFactory.setConcurrency("1-1");
    defaultJmsContainerFactory.setMessageConverter(jacksonJmsConvertor());
    defaultJmsContainerFactory.setErrorHandler(t -> log.info("Handling error in listener for messages, error: {}", t.getMessage()));
    return defaultJmsContainerFactory;
  }

}
