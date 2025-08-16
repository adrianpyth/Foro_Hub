package com.alura.foroHub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Verificar duplicados por título y mensaje
    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    // Verificar duplicados excluyendo el ID actual (para actualizaciones)
    boolean existsByTituloAndMensajeAndIdNot(String titulo, String mensaje, Long id);

    // Buscar por nombre de curso
    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :nombreCurso")
    Page<Topico> findByCursoNombre(@Param("nombreCurso") String nombreCurso, Pageable pageable);

    // Buscar por año específico
    @Query("SELECT t FROM Topico t WHERE YEAR(t.fechaCreacion) = :anio")
    Page<Topico> findByAnio(@Param("anio") Integer anio, Pageable pageable);

    // Buscar por curso y año
    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :nombreCurso AND YEAR(t.fechaCreacion) = :anio")
    Page<Topico> findByCursoNombreAndAnio(@Param("nombreCurso") String nombreCurso, @Param("anio") Integer anio, Pageable pageable);

    // Obtener los primeros 10 resultados ordenados por fecha ASC
    @Query("SELECT t FROM Topico t ORDER BY t.fechaCreacion ASC")
    Page<Topico> findFirst10ByOrderByFechaCreacionAsc(Pageable pageable);
}
