package com.alura.foroHub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosRegistroUsuario(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El formato del correo electrónico no es válido")
        String correoElectronico,

        @NotBlank(message = "La contraseña es obligatoria")
        String contrasena
        // ❌ Eliminado: Long perfilId
) {}