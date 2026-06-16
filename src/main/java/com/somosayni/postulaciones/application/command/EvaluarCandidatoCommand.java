package com.somosayni.postulaciones.application.command;

public record EvaluarCandidatoCommand(
        String postulacionId,
        String reclutadorId,
        int puntuacion,
        String feedback,
        String resultado
) {}
