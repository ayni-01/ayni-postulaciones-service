package com.somosayni.postulaciones.infrastructure.persistence.repository;

import com.somosayni.postulaciones.infrastructure.persistence.entity.PostulacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaPostulacionRepository extends JpaRepository<PostulacionEntity, String> {
    List<PostulacionEntity> findByRetoId(String retoId);
    List<PostulacionEntity> findByTalentoId(String talentoId);
    Optional<PostulacionEntity> findByTalentoIdAndRetoId(String talentoId, String retoId);
}
