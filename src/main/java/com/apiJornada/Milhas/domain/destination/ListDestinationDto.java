package com.apiJornada.Milhas.domain.destination;

import java.math.BigDecimal;

public record ListDestinationDto(
    Long id,
    byte[] picture,
    String name,
    BigDecimal price) {

  public ListDestinationDto(Destination entity) {
    this(entity.getId(), entity.getPicture(), entity.getName(), entity.getPrice());
  }
}
