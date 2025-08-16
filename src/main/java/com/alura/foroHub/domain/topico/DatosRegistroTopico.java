package com.alura.foroHub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotBlank(message = "El título es obligatorio")
        String titulo,

        @NotBlank(message = "El mensaje es obligatorio")
        String mensaje,

        @NotNull(message = "El autor es obligatorio")
        Long autor,

        @NotNull(message = "El curso es obligatorio")
        Long curso
) {}
