package com.apiJornada.Milhas.domain.destination;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;

public record GenerateDestinationDescriptionDto(
  @NotBlank @JsonAlias("name") String destinationName
) {
  
}
