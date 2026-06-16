package com.somosayni.postulaciones.application.command;

import com.somosayni.postulaciones.application.port.PostulacionRepository;
import com.somosayni.postulaciones.domain.model.Postulacion;
import org.springframework.stereotype.Component;

@Component
public class PostularARetoCommandHandler {

    private final PostulacionRepository repository;

    public PostularARetoCommandHandler(PostulacionRepository repository) {
        this.repository = repository;
    }

    public Postulacion handle(PostularARetoCommand command) {
        if (repository.findByTalentoIdAndRetoId(command.talentoId(), command.retoId()).isPresent()) {
            throw new IllegalStateException("Ya te has postulado a este reto");
        }

        Postulacion postulacion = new Postulacion(command.talentoId(), command.retoId(), command.urlSolucion());
        return repository.save(postulacion);
    }
}
