package com.somosayni.postulaciones.domain.model;

import com.somosayni.shared.domain.model.AggregateRoot;

public class Evaluacion extends AggregateRoot {

    private String postulacionId;
    private String reclutadorId;
    private int puntuacion;
    private String feedback;
    private ResultadoEvaluacion resultado;

    public Evaluacion() {}

    public Evaluacion(String postulacionId, String reclutadorId, int puntuacion) {
        this.postulacionId = postulacionId;
        this.reclutadorId = reclutadorId;
        setPuntuacion(puntuacion);
    }

    public void setPuntuacion(int puntuacion) {
        if (puntuacion < 0 || puntuacion > 100) {
            throw new IllegalArgumentException("Puntuación debe estar entre 0 y 100");
        }
        this.puntuacion = puntuacion;
    }

    public void aprobar(String feedback) {
        this.resultado = ResultadoEvaluacion.APROBADO;
        this.feedback = feedback;
    }

    public void rechazar(String feedback) {
        if (feedback == null || feedback.isBlank()) {
            throw new IllegalArgumentException("Feedback obligatorio para rechazo");
        }
        this.resultado = ResultadoEvaluacion.RECHAZADO;
        this.feedback = feedback;
    }

    public String getPostulacionId() { return postulacionId; }
    public String getReclutadorId() { return reclutadorId; }
    public int getPuntuacion() { return puntuacion; }
    public String getFeedback() { return feedback; }
    public ResultadoEvaluacion getResultado() { return resultado; }

    public void setFeedback(String feedback) { this.feedback = feedback; }

    public enum ResultadoEvaluacion {
        APROBADO, RECHAZADO
    }
}
