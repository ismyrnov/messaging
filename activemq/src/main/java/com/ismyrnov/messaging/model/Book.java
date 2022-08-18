package com.ismyrnov.messaging.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonDeserialize(builder = Book.BookBuilder.class)
public class Book {
  private final String id;
  private final String title;

  @JsonPOJOBuilder(withPrefix="")
  public static class BookBuilder {}
}
