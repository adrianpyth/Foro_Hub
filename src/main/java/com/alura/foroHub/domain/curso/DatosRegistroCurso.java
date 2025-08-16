package com.alura.foroHub.domain.curso;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroCurso(
        @NotBlank(message = "El nombre del curso es obligatorio")
        String nombre,

        @NotBlank(message = "La categoría del curso es obligatoria")
        String categoria
) {}
