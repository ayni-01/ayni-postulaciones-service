package com.somosayni.postulaciones.domain.repository;

import com.somosayni.postulaciones.domain.model.Postulacion;
import java.util.List;
import java.util.Optional;

public interface PostulacionRepository {
    Optional<Postulacion> findById(String id);
    List<Postulacion> findByRetoId(String retoId);
    List<Postulacion> findByTalentoId(String talentoId);
    Optional<Postulacion> findByTalentoIdAndRetoId(String talentoId, String retoId);
    Postulacion save(Postulacion postulacion);
    void deleteById(String id);
}
