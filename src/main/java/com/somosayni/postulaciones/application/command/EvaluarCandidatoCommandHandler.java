package com.somosayni.postulaciones.application.command;

import com.somosayni.postulaciones.application.port.EvaluacionRepository;
import com.somosayni.postulaciones.application.port.PostulacionRepository;
import com.somosayni.postulaciones.domain.model.Evaluacion;
import com.somosayni.postulaciones.domain.model.Postulacion;
import org.springframework.stereotype.Component;

@Component
public class EvaluarCandidatoCommandHandler {

    private final PostulacionRepository postulacionRepository;
    private final EvaluacionRepository evaluacionRepository;

    public EvaluarCandidatoCommandHandler(PostulacionRepository postulacionRepository, EvaluacionRepository evaluacionRepository) {
        this.postulacionRepository = postulacionRepository;
        this.evaluacionRepository = evaluacionRepository;
    }

    public Evaluacion handle(EvaluarCandidatoCommand command) {
        Postulacion postulacion = postulacionRepository.findById(command.postulacionId())
                .orElseThrow(() -> new IllegalArgumentException("Postulación no encontrada"));

        Evaluacion evaluacion = new Evaluacion(command.postulacionId(), command.reclutadorId(), command.puntuacion());

        if (command.resultado().equalsIgnoreCase("APROBADO")) {
            evaluacion.aprobar(command.feedback());
            postulacion.aprobar();
        } else {
            evaluacion.rechazar(command.feedback());
            postulacion.rechazar();
        }

        postulacionRepository.save(postulacion);
        return evaluacionRepository.save(evaluacion);
    }
}
