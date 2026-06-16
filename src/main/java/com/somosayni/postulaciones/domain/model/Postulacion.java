package com.somosayni.postulaciones.domain.model;

import com.somosayni.shared.domain.model.AggregateRoot;
import java.time.LocalDateTime;

public class Postulacion extends AggregateRoot {

    private String talentoId;
    private String retoId;
    private String urlSolucion;
    private LocalDateTime fechaEnvio;
    private EstadoPostulacion estado;

    public Postulacion() {}

    public Postulacion(String talentoId, String retoId, String urlSolucion) {
        this.talentoId = talentoId;
        this.retoId = retoId;
        this.urlSolucion = urlSolucion;
        this.fechaEnvio = LocalDateTime.now();
        this.estado = EstadoPostulacion.EN_REVISION;
    }

    public void aprobar() {
        this.estado = EstadoPostulacion.APROBADO;
        registerEvent(new AggregateRoot.DomainEvent("CandidatoAprobado", this.getId(), LocalDateTime.now()));
    }

    public void rechazar() {
        this.estado = EstadoPostulacion.RECHAZADO;
        registerEvent(new AggregateRoot.DomainEvent("CandidatoRechazado", this.getId(), LocalDateTime.now()));
    }

    public String getTalentoId() { return talentoId; }
    public String getRetoId() { return retoId; }
    public String getUrlSolucion() { return urlSolucion; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public EstadoPostulacion getEstado() { return estado; }

    public void setEstado(EstadoPostulacion estado) { this.estado = estado; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public enum EstadoPostulacion {
        EN_REVISION, APROBADO, RECHAZADO
    }
}
