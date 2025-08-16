package com.alura.foroHub.domain.topico;

import com.alura.foroHub.domain.curso.CursoRepository;
import com.alura.foroHub.domain.usuario.UsuarioRepository;
import com.alura.foroHub.infra.exceptions.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public DatosDetalleTopico registrarTopico(DatosRegistroTopico datos) {

        // Validar que no exista un tópico duplicado
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ValidacionException("Ya existe un tópico con el mismo título y mensaje");
        }

        // Validar que el usuario existe
        var usuario = usuarioRepository.findById(datos.autor())
                .orElseThrow(() -> new ValidacionException("Usuario no encontrado"));

        // Validar que el curso existe
        var curso = cursoRepository.findById(datos.curso())
                .orElseThrow(() -> new ValidacionException("Curso no encontrado"));

        var topico = new Topico(datos.titulo(), datos.mensaje(), usuario, curso);
        topicoRepository.save(topico);

        return new DatosDetalleTopico(topico);
    }

    public Page<DatosListadoTopico> listarTopicos(Pageable paginacion, String curso, Integer anio) {
        Page<Topico> topicos;

        if (curso != null && anio != null) {
            topicos = topicoRepository.findByCursoNombreAndAnio(curso, anio, paginacion);
        } else if (curso != null) {
            topicos = topicoRepository.findByCursoNombre(curso, paginacion);
        } else if (anio != null) {
            topicos = topicoRepository.findByAnio(anio, paginacion);
        } else {
            // Ordenar por fecha de creación ASC por defecto
            Pageable paginacionOrdenada = PageRequest.of(
                    paginacion.getPageNumber(),
                    paginacion.getPageSize(),
                    Sort.by(Sort.Direction.ASC, "fechaCreacion")
            );
            topicos = topicoRepository.findAll(paginacionOrdenada);
        }

        return topicos.map(DatosListadoTopico::new);
    }

    public DatosDetalleTopico obtenerTopicoPorId(Long id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Tópico no encontrado"));

        return new DatosDetalleTopico(topico);
    }

    public DatosDetalleTopico actualizarTopico(Long id, DatosActualizacionTopico datos) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Tópico no encontrado"));

        // Validar duplicados solo si se están cambiando título o mensaje
        if (datos.titulo() != null || datos.mensaje() != null) {
            String nuevoTitulo = datos.titulo() != null ? datos.titulo() : topico.getTitulo();
            String nuevoMensaje = datos.mensaje() != null ? datos.mensaje() : topico.getMensaje();

            if (topicoRepository.existsByTituloAndMensajeAndIdNot(nuevoTitulo, nuevoMensaje, id)) {
                throw new ValidacionException("Ya existe un tópico con el mismo título y mensaje");
            }
        }

        topico.actualizarDatos(datos);
        return new DatosDetalleTopico(topico);
    }

    public void eliminarTopico(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new ValidacionException("Tópico no encontrado");
        }
        topicoRepository.deleteById(id);
    }
}

