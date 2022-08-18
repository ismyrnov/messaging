package com.ismyrnov.messaging.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonDeserialize(builder = BookOrder.BookOrderBuilder.class)
public class BookOrder {
  private final String id;
  private final Book book;
  private final Customer customer;

  @JsonPOJOBuilder(withPrefix="")
  public static class BookOrderBuilder {}
}
