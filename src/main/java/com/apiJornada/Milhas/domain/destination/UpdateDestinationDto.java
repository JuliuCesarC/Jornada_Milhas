package com.apiJornada.Milhas.domain.destination;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateDestinationDto(
    @NotNull Long id,
    MultipartFile picture,
    @Size(min = 3, max = 100, message = "Nome deve conter entre 3 a 100 caracteres.") String name,
    BigDecimal price) {

}
