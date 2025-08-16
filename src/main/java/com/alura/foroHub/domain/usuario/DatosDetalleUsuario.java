package com.alura.foroHub.domain.usuario;

public record DatosDetalleUsuario(
        Long id,
        String nombre,
        String correoElectronico,
        String perfil
) {
    public DatosDetalleUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoElectronico(),
                usuario.getPerfil().getNombre()
        );
    }
}
