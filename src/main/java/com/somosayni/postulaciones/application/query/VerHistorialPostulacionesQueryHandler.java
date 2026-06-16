package com.somosayni.postulaciones.application.query;

import com.somosayni.postulaciones.application.port.PostulacionRepository;
import com.somosayni.postulaciones.domain.model.Postulacion;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class VerHistorialPostulacionesQueryHandler {

    private final PostulacionRepository repository;

    public VerHistorialPostulacionesQueryHandler(PostulacionRepository repository) {
        this.repository = repository;
    }

    public List<Postulacion> handle(VerHistorialPostulacionesQuery query) {
        return repository.findByTalentoId(query.talentoId());
    }
}
