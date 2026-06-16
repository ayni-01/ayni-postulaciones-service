package com.somosayni.postulaciones.application.query;

import com.somosayni.postulaciones.application.port.PostulacionRepository;
import com.somosayni.postulaciones.domain.model.Postulacion;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class VerListaSolucionesQueryHandler {

    private final PostulacionRepository repository;

    public VerListaSolucionesQueryHandler(PostulacionRepository repository) {
        this.repository = repository;
    }

    public List<Postulacion> handle(VerListaSolucionesQuery query) {
        return repository.findByRetoId(query.retoId());
    }
}
