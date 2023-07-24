package com.apiJornada.Milhas.domain.destination;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateDestinationDto(
    @NotNull Long id,
    MultipartFile imageOne,
    MultipartFile imageTwo,

    @Size(min = 3, max = 100, message = "Nome deve conter entre 3 a 100 caracteres.") String name,
    @Size(min = 3, max = 160, message = "Meta deve conter entre 3 a 160 caracteres.") String target,
    @Size(min = 50, max = 450, message = "Descrição deve conter entre 50 a 450 caracteres.") String destinationDescription) {

}
