package com.alura.foroHub.domain.usuario;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "perfil")
@Entity(name = "Perfil")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    public Perfil(String nombre) {
        this.nombre = nombre;
    }
}
