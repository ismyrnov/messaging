package com.ismyrnov.messaging.config;

import ch.qos.logback.classic.Logger;
import com.ismyrnov.messaging.listener.BookOrderProcessingMessageListener;
import com.ismyrnov.messaging.model.Book;
import com.ismyrnov.messaging.model.BookOrder;
import com.ismyrnov.messaging.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;

import static com.ismyrnov.messaging.secvice.jms.WarehouseProcessingService.BOOK_PROCESSED_QUEUE;

@Slf4j
@EnableTransactionManagement
@EnableJms
@Configuration
@PropertySource(value = "application.yml")
@EnableConfigurationProperties(JmsProperties.class)
public class JmsConfig {// implements JmsListenerConfigurer {

  @Bean
  public MessageConverter jacksonJmsConvertor() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }

//  @Bean
//  public XStreamMarshaller xmlMarshaller() {
//    XStreamMarshaller marshaller = new XStreamMarshaller();
//    marshaller.setSupportedClasses(Book.class, Customer.class, BookOrder.class);
//    return marshaller;
//  }
//
//  @Bean
//  public MessageConverter xmlMarshallingMessageConvertor(XStreamMarshaller xmlMarshaller) {
//    MarshallingMessageConverter converter = new MarshallingMessageConverter(xmlMarshaller);
//    converter.setTargetType(MessageType.TEXT);
//    return converter;
//  }

  @Bean
  public JmsListenerContainerFactory warehouseFactory(ConnectionFactory factory,
                                                      DefaultJmsListenerContainerFactoryConfigurer configurer) {
    DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
    configurer.configure(containerFactory, factory);
    return containerFactory;
  }

  @Bean
  public CachingConnectionFactory connectionFactory(JmsProperties jmsProperties) {
    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(jmsProperties.getUser(), jmsProperties.getPassword(), jmsProperties.getBrokerUrl());
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory(factory);
//    connectionFactory.setReconnectOnException(true); // CachingConnectionFactory by default set true
    connectionFactory.setClientId("my-client-id");
    connectionFactory.setSessionCacheSize(100);
    return connectionFactory;
  }

  @Bean
  public PlatformTransactionManager jmsTransactionManager(ConnectionFactory connectionFactory) {
    return new JmsTransactionManager(connectionFactory);
  }

//  @Bean
//  public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
//    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
//    jmsTemplate.setMessageConverter();
//    return jmsTemplate;
//  }

  @Bean
  public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
    jmsTemplate.setMessageConverter(jacksonJmsConvertor());
    jmsTemplate.setDeliveryPersistent(true);
    jmsTemplate.setSessionTransacted(true);
    return jmsTemplate;
  }

  @Bean
  public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               PlatformTransactionManager jmsTransactionManager) {
    DefaultJmsListenerContainerFactory defaultJmsContainerFactory = new DefaultJmsListenerContainerFactory();
//    defaultJmsContainerFactory.setConnectionFactory(connectionFactory());
    defaultJmsContainerFactory.setConnectionFactory(connectionFactory);
    defaultJmsContainerFactory.setConcurrency("1-1");
    defaultJmsContainerFactory.setMessageConverter(jacksonJmsConvertor());
    defaultJmsContainerFactory.setTransactionManager(jmsTransactionManager);
    defaultJmsContainerFactory.setErrorHandler(t -> log.info("Handling error in listener for messages, error: {}", t.getMessage()));
//    defaultJmsContainerFactory.setMessageConverter(xmlMarshallingMessageConvertor);
    return defaultJmsContainerFactory;
  }

//  @Bean
//  public BookOrderProcessingMessageListener jmsMessageListener() {
//    BookOrderProcessingMessageListener messageListener = new BookOrderProcessingMessageListener();
//    return messageListener;
//  }
//
//  @Override
//  public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
//    SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
//    endpoint.setMessageListener(jmsMessageListener());
//    endpoint.setDestination(BOOK_PROCESSED_QUEUE);
//    endpoint.setId("book-order-processed-queue");
//    endpoint.setSubscription("my-subscription");
//    endpoint.setConcurrency("1");
//    registrar.setContainerFactory(defaultJmsListenerContainerFactory());
//    registrar.registerEndpoint(endpoint, defaultJmsListenerContainerFactory());
//  }
}
