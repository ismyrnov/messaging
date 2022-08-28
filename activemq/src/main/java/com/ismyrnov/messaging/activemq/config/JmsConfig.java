package com.ismyrnov.messaging.activemq.config;

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
  public SingleConnectionFactory connectionFactory(JmsProperties jmsProperties) {
    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(jmsProperties.getUser(), jmsProperties.getPassword(), jmsProperties.getBrokerUrl());
    SingleConnectionFactory connectionFactory = new SingleConnectionFactory(factory);
    connectionFactory.setReconnectOnException(true);
    connectionFactory.setClientId("client-id-1");
    return connectionFactory;
  }

  @Bean(name = "queueJmsTemplate")
  public JmsTemplate queueJmsTemplate(MessageConverter jacksonJmsConvertor, SingleConnectionFactory connectionFactory) {
    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
    jmsTemplate.setMessageConverter(jacksonJmsConvertor);
    return jmsTemplate;
  }

  @Bean(name = "topicJmsTemplate")
  public JmsTemplate topicJmsTemplate(MessageConverter jacksonJmsConvertor, SingleConnectionFactory connectionFactory) {
    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
    jmsTemplate.setMessageConverter(jacksonJmsConvertor);
    jmsTemplate.setPubSubDomain(true);
    return jmsTemplate;
  }

  @Bean
  public DefaultJmsListenerContainerFactory durableJmsContainerFactory(SingleConnectionFactory clientAConnectionFactory) {
    DefaultJmsListenerContainerFactory defaultJmsContainerFactory = buildJmsListenerContainerFactory(clientAConnectionFactory);
    defaultJmsContainerFactory.setPubSubDomain(true);
    defaultJmsContainerFactory.setConcurrency("1-1");

    defaultJmsContainerFactory.setSubscriptionDurable(true);
    return defaultJmsContainerFactory;
  }

  @Bean
  public DefaultJmsListenerContainerFactory nonDurableJmsContainerFactory(SingleConnectionFactory clientBConnectionFactory) {
    DefaultJmsListenerContainerFactory defaultJmsContainerFactory = buildJmsListenerContainerFactory(clientBConnectionFactory);
    defaultJmsContainerFactory.setPubSubDomain(true);
    defaultJmsContainerFactory.setConcurrency("1-1");

    return defaultJmsContainerFactory;
  }

  @Bean
  public DefaultJmsListenerContainerFactory defaultJmsContainerFactory(SingleConnectionFactory clientBConnectionFactory) {
    return buildJmsListenerContainerFactory(clientBConnectionFactory);
  }

  private DefaultJmsListenerContainerFactory buildJmsListenerContainerFactory(SingleConnectionFactory connectionFactory) {
    DefaultJmsListenerContainerFactory defaultJmsContainerFactory = new DefaultJmsListenerContainerFactory();
    defaultJmsContainerFactory.setConnectionFactory(connectionFactory);
    defaultJmsContainerFactory.setMessageConverter(jacksonJmsConvertor());
    defaultJmsContainerFactory.setErrorHandler(t -> log.info("Handling error in listener for messages, error: {}", t.getMessage()));
    return defaultJmsContainerFactory;
  }
}
