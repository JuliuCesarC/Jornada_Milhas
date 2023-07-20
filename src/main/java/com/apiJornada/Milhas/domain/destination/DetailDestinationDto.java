package com.apiJornada.Milhas.domain.destination;

import java.math.BigDecimal;

public record DetailDestinationDto(
    Long id,
    byte[] picture,
    String name,
    BigDecimal price,
    Boolean active) {
  public DetailDestinationDto(Destination entity) {
    this(entity.getId(), entity.getPicture(), entity.getName(), entity.getPrice(), entity.getActive());
  }

}
