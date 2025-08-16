package com.alura.foroHub.domain.topico;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleTopico> registrarTopico(
            @RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
            UriComponentsBuilder uriComponentsBuilder) {

        var topico = topicoService.registrarTopico(datosRegistroTopico);

        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.id()).toUri();
        return ResponseEntity.created(uri).body(topico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion,
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer anio) {

        Page<DatosListadoTopico> topicos = topicoService.listarTopicos(paginacion, curso, anio);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> detallarTopico(@PathVariable Long id) {
        var topico = topicoService.obtenerTopicoPorId(id);
        return ResponseEntity.ok(topico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetalleTopico> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizacionTopico datosActualizacionTopico) {

        var topico = topicoService.actualizarTopico(id, datosActualizacionTopico);
        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
