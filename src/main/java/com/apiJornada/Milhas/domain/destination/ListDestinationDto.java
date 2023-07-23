package com.apiJornada.Milhas.domain.destination;

public record ListDestinationDto(
    Long id,
    byte[] imageOne,
    byte[] imageTwo,
    String name,
    String target,
    String destinationDescription) {

  public ListDestinationDto(Destination entity) {
    this(entity.getId(), entity.getImageOne(), entity.getImageTwo(), entity.getName(), entity.getTarget(),
        entity.getDestinationDescription());
  }
}
