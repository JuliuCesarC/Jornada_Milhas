package com.apiJornada.Milhas.domain.destination;

import java.math.BigDecimal;

public record ListDestinationDto(
    Long id,
    byte[] imageOne,
    byte[] imageTwo,
    String name,
    String target,
    BigDecimal price) {

  public ListDestinationDto(Destination entity) {
    this(entity.getId(), entity.getImageOne(), entity.getImageTwo(), entity.getName(), entity.getTarget(), entity.getPrice());
  }
}
