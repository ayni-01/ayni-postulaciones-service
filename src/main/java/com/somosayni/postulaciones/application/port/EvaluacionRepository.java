package com.somosayni.postulaciones.application.port;

import com.somosayni.postulaciones.domain.model.Evaluacion;
import java.util.List;
import java.util.Optional;

public interface EvaluacionRepository {
    Optional<Evaluacion> findById(String id);
    Optional<Evaluacion> findByPostulacionId(String postulacionId);
    List<Evaluacion> findByReclutadorId(String reclutadorId);
    Evaluacion save(Evaluacion evaluacion);
}
