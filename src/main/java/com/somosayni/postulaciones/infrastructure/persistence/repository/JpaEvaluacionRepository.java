package com.somosayni.postulaciones.infrastructure.persistence.repository;

import com.somosayni.postulaciones.infrastructure.persistence.entity.EvaluacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaEvaluacionRepository extends JpaRepository<EvaluacionEntity, String> {
    Optional<EvaluacionEntity> findByPostulacionId(String postulacionId);
    List<EvaluacionEntity> findByReclutadorId(String reclutadorId);
}
