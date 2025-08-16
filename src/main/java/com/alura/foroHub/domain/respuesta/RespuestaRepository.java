package com.alura.foroHub.domain.respuesta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    List<Respuesta> findByTopicoIdOrderByFechaCreacionAsc(Long topicoId);

    @Query("SELECT COUNT(r) FROM Respuesta r WHERE r.topico.id = :topicoId")
    Integer countByTopicoId(@Param("topicoId") Long topicoId);
}
