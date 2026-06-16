package com.somosayni.postulaciones.infrastructure.persistence.mapper;

import com.somosayni.postulaciones.application.port.PostulacionRepository;
import com.somosayni.postulaciones.domain.model.Postulacion;
import com.somosayni.postulaciones.infrastructure.persistence.entity.PostulacionEntity;
import com.somosayni.postulaciones.infrastructure.persistence.repository.JpaPostulacionRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class PostulacionRepositoryImpl implements PostulacionRepository {

    private final JpaPostulacionRepository jpaRepository;

    public PostulacionRepositoryImpl(JpaPostulacionRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public List<Postulacion> findAll() { return jpaRepository.findAll().stream().map(PostulacionEntity::toDomain).toList(); }

    @Override
    public Postulacion save(Postulacion p) {
        if (p.getId() != null) {
            return jpaRepository.findById(p.getId())
                .map(entity -> {
                    entity.setEstado(p.getEstado());
                    return jpaRepository.save(entity).toDomain();
                })
                .orElseGet(() -> jpaRepository.save(PostulacionEntity.fromDomain(p)).toDomain());
        }
        return jpaRepository.save(PostulacionEntity.fromDomain(p)).toDomain();
    }

    @Override
    public void deleteById(String id) { jpaRepository.deleteById(id); }

    @Override
    public List<Postulacion> findByRetoId(String retoId) { return jpaRepository.findByRetoId(retoId).stream().map(PostulacionEntity::toDomain).toList(); }

    @Override
    public List<Postulacion> findByTalentoId(String talentoId) { return jpaRepository.findByTalentoId(talentoId).stream().map(PostulacionEntity::toDomain).toList(); }

    @Override
    public Optional<Postulacion> findById(String id) { return jpaRepository.findById(id).map(PostulacionEntity::toDomain); }

    @Override
    public Optional<Postulacion> findByTalentoIdAndRetoId(String talentoId, String retoId) { return jpaRepository.findByTalentoIdAndRetoId(talentoId, retoId).map(PostulacionEntity::toDomain); }
}
