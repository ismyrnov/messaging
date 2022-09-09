package com.ismyrnov.messaging.kafka.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
@JsonDeserialize(builder = Taxi.TaxiBuilder.class)
public class Taxi {

  private final String carId;
  protected final Integer startPoint;
  protected final Integer endPoint;

  @JsonPOJOBuilder(withPrefix="")
  public static class TaxiBuilder {}
}
