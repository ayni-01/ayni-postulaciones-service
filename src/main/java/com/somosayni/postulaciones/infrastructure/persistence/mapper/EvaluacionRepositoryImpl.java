package com.somosayni.postulaciones.infrastructure.persistence.mapper;

import com.somosayni.postulaciones.application.port.EvaluacionRepository;
import com.somosayni.postulaciones.domain.model.Evaluacion;
import com.somosayni.postulaciones.infrastructure.persistence.entity.EvaluacionEntity;
import com.somosayni.postulaciones.infrastructure.persistence.repository.JpaEvaluacionRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class EvaluacionRepositoryImpl implements EvaluacionRepository {

    private final JpaEvaluacionRepository jpaRepository;

    public EvaluacionRepositoryImpl(JpaEvaluacionRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public List<Evaluacion> findAll() { return jpaRepository.findAll().stream().map(EvaluacionEntity::toDomain).toList(); }

    @Override
    public Evaluacion save(Evaluacion e) { return jpaRepository.save(EvaluacionEntity.fromDomain(e)).toDomain(); }

    @Override
    public List<Evaluacion> findByReclutadorId(String reclutadorId) { return jpaRepository.findByReclutadorId(reclutadorId).stream().map(EvaluacionEntity::toDomain).toList(); }

    @Override
    public Optional<Evaluacion> findById(String id) { return jpaRepository.findById(id).map(EvaluacionEntity::toDomain); }

    @Override
    public Optional<Evaluacion> findByPostulacionId(String postulacionId) { return jpaRepository.findByPostulacionId(postulacionId).map(EvaluacionEntity::toDomain); }
}
