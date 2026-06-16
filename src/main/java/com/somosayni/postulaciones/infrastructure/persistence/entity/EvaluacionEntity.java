package com.somosayni.postulaciones.infrastructure.persistence.entity;

import com.somosayni.postulaciones.domain.model.Evaluacion;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "evaluacion")
public class EvaluacionEntity {

    @Id
    private String id;

    @Column(name = "postulacion_id", nullable = false, unique = true)
    private String postulacionId;

    @Column(name = "reclutador_id", nullable = false)
    private String reclutadorId;

    @Column(nullable = false)
    private int puntuacion;

    @Column(length = 2000)
    private String feedback;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Evaluacion.ResultadoEvaluacion resultado;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (id == null) id = UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
    }

    public Evaluacion toDomain() {
        Evaluacion e = new Evaluacion(postulacionId, reclutadorId, puntuacion);
        e.setId(id);
        e.setFeedback(feedback);
        if (resultado == Evaluacion.ResultadoEvaluacion.APROBADO) {
            e.aprobar(feedback);
        } else {
            e.rechazar(feedback);
        }
        return e;
    }

    public static EvaluacionEntity fromDomain(Evaluacion e) {
        EvaluacionEntity entity = new EvaluacionEntity();
        entity.id = e.getId();
        entity.postulacionId = e.getPostulacionId();
        entity.reclutadorId = e.getReclutadorId();
        entity.puntuacion = e.getPuntuacion();
        entity.feedback = e.getFeedback();
        entity.resultado = e.getResultado();
        return entity;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPostulacionId() { return postulacionId; }
    public void setPostulacionId(String postulacionId) { this.postulacionId = postulacionId; }
    public String getReclutadorId() { return reclutadorId; }
    public void setReclutadorId(String reclutadorId) { this.reclutadorId = reclutadorId; }
    public int getPuntuacion() { return puntuacion; }
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public Evaluacion.ResultadoEvaluacion getResultado() { return resultado; }
    public void setResultado(Evaluacion.ResultadoEvaluacion resultado) { this.resultado = resultado; }
}
