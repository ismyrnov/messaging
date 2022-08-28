package com.ismyrnov.messaging.activemq.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonDeserialize(builder = Customer.CustomerBuilder.class)
public class Customer {
  private final String id;
  private final String fullName;

  @JsonPOJOBuilder(withPrefix="")
  public static class CustomerBuilder {}
}
