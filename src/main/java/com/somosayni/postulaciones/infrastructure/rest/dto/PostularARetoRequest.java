package com.somosayni.postulaciones.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record PostularARetoRequest(
        @NotBlank(message = "Reto ID es obligatorio")
        String retoId,

        @NotBlank(message = "URL de solución es obligatoria")
        String urlSolucion
) {}
