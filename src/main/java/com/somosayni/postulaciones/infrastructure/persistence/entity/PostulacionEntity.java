package com.somosayni.postulaciones.infrastructure.persistence.entity;

import com.somosayni.postulaciones.domain.model.Postulacion;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "postulacion")
public class PostulacionEntity {

    @Id
    private String id;

    @Column(name = "talento_id", nullable = false)
    private String talentoId;

    @Column(name = "reto_id", nullable = false)
    private String retoId;

    @Column(name = "url_solucion", nullable = false)
    private String urlSolucion;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Postulacion.EstadoPostulacion estado;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (id == null) id = UUID.randomUUID().toString();
        fechaEnvio = LocalDateTime.now();
        createdAt = LocalDateTime.now();
    }

    public Postulacion toDomain() {
        Postulacion p = new Postulacion(talentoId, retoId, urlSolucion);
        p.setId(id);
        p.setEstado(estado);
        return p;
    }

    public static PostulacionEntity fromDomain(Postulacion p) {
        PostulacionEntity e = new PostulacionEntity();
        e.id = p.getId();
        e.talentoId = p.getTalentoId();
        e.retoId = p.getRetoId();
        e.urlSolucion = p.getUrlSolucion();
        e.estado = p.getEstado();
        return e;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTalentoId() { return talentoId; }
    public void setTalentoId(String talentoId) { this.talentoId = talentoId; }
    public String getRetoId() { return retoId; }
    public void setRetoId(String retoId) { this.retoId = retoId; }
    public String getUrlSolucion() { return urlSolucion; }
    public void setUrlSolucion(String urlSolucion) { this.urlSolucion = urlSolucion; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
    public Postulacion.EstadoPostulacion getEstado() { return estado; }
    public void setEstado(Postulacion.EstadoPostulacion estado) { this.estado = estado; }
}
