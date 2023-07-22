package com.apiJornada.Milhas.domain.destination;

public record DetailDestinationDto(
    Long id,
    byte[] image1,
    byte[] image2,
    String name,
    String target,
    String description,
    Boolean active) {
  public DetailDestinationDto(Destination entity) {
    this(entity.getId(), entity.getImageOne(), entity.getImageTwo(), entity.getName(), entity.getTarget(),
        entity.getDescription(), entity.getActive());
  }

}
