package com.somosayni.postulaciones.application.command;

public record PostularARetoCommand(
        String talentoId,
        String retoId,
        String urlSolucion
) {}
