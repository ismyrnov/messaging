package com.ismyrnov.messaging.activemq.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonDeserialize(builder = ProcessedBookOrder.ProcessedBookOrderBuilder.class)
public class ProcessedBookOrder {
  private final BookOrder bookOrder;
  private final Date processingDateTime;
  private final Date expectedShippingDateTime;

  @JsonPOJOBuilder(withPrefix="")
  public static class ProcessedBookOrderBuilder {}
}
