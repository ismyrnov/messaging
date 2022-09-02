package com.ismyrnov.messaging.rabbitmq.repository;

import com.ismyrnov.messaging.rabbitmq.model.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Long> {
}
