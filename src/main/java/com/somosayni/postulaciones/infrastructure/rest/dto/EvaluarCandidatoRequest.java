package com.somosayni.postulaciones.infrastructure.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record EvaluarCandidatoRequest(
        @Min(value = 0, message = "Puntuación mínima 0")
        @Max(value = 100, message = "Puntuación máxima 100")
        int puntuacion,

        String feedback,

        @NotBlank(message = "Resultado es obligatorio")
        String resultado
) {}
