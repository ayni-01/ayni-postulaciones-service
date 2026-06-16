package com.somosayni.postulaciones.infrastructure.rest;

import com.somosayni.postulaciones.application.command.*;
import com.somosayni.postulaciones.application.query.*;
import com.somosayni.postulaciones.domain.model.Postulacion;
import com.somosayni.postulaciones.domain.model.Evaluacion;
import com.somosayni.postulaciones.infrastructure.rest.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/postulaciones")
public class PostulacionController {

    private final PostularARetoCommandHandler postearHandler;
    private final EvaluarCandidatoCommandHandler evaluarHandler;
    private final VerHistorialPostulacionesQueryHandler historialHandler;
    private final VerListaSolucionesQueryHandler solucionesHandler;

    public PostulacionController(
            PostularARetoCommandHandler postearHandler,
            EvaluarCandidatoCommandHandler evaluarHandler,
            VerHistorialPostulacionesQueryHandler historialHandler,
            VerListaSolucionesQueryHandler solucionesHandler) {
        this.postearHandler = postearHandler;
        this.evaluarHandler = evaluarHandler;
        this.historialHandler = historialHandler;
        this.solucionesHandler = solucionesHandler;
    }

    @PostMapping
    public ResponseEntity<Postulacion> postentar(
            @RequestHeader("X-User-Id") String talentoId,
            @Valid @RequestBody PostularARetoRequest request) {
        Postulacion p = postearHandler.handle(new PostularARetoCommand(talentoId, request.retoId(), request.urlSolucion()));
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    @GetMapping("/talento/{talentoId}")
    public ResponseEntity<List<Postulacion>> verHistorial(@PathVariable String talentoId) {
        return ResponseEntity.ok(historialHandler.handle(new VerHistorialPostulacionesQuery(talentoId)));
    }

    @GetMapping("/reto/{retoId}")
    public ResponseEntity<List<Postulacion>> verSoluciones(@PathVariable String retoId) {
        return ResponseEntity.ok(solucionesHandler.handle(new VerListaSolucionesQuery(retoId)));
    }

    @PostMapping("/{id}/evaluar")
    public ResponseEntity<Evaluacion> evaluar(
            @PathVariable String id,
            @RequestHeader("X-User-Id") String reclutadorId,
            @Valid @RequestBody EvaluarCandidatoRequest request) {
        Evaluacion e = evaluarHandler.handle(new EvaluarCandidatoCommand(id, reclutadorId, request.puntuacion(), request.feedback(), request.resultado()));
        return ResponseEntity.ok(e);
    }
}
