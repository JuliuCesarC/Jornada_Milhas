package com.apiJornada.Milhas.domain.destination;

import java.math.BigDecimal;

public record DetailDestinationDto(
    Long id,
    byte[] image1,
    byte[] image2,
    String name,
    String target,
    String destinationDescription,
    BigDecimal price,
    Boolean active) {
  public DetailDestinationDto(Destination entity) {
    this(entity.getId(), entity.getImageOne(), entity.getImageTwo(), entity.getName(), entity.getTarget(),
        entity.getDestinationDescription(), entity.getPrice(), entity.getActive());
  }

}
