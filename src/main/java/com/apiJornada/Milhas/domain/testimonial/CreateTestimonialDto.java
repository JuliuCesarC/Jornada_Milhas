package com.apiJornada.Milhas.domain.testimonial;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTestimonialDto(

        @NotNull(message = "Arquivo não deve estar vazio.") MultipartFile picture,
        @NotBlank @Size(min = 3, max = 100, message = "Nome deve conter entre 3 a 100 caracteres.") String name,
        @NotBlank @Size(max = 400, message = "Depoimento pode conter no máximo 400 caracteres.") String testimonial

) {
}
