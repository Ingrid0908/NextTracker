package com.project.NextTracker.controller;

import com.project.NextTracker.dto.EvaluacionDTO;
import com.project.NextTracker.model.Evaluacion;
import com.project.NextTracker.service.EvaluacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/malla/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    public EvaluacionController(
            EvaluacionService evaluacionService
    ) {
        this.evaluacionService = evaluacionService;
    }

    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<Evaluacion>> getEvaluacionesCurso(
            @PathVariable Integer idCurso
    ) {

        return ResponseEntity.ok(
                evaluacionService.findByCursoId(idCurso)
        );
    }

    @PostMapping
    public ResponseEntity<Evaluacion> create(
            @RequestBody EvaluacionDTO evaluacionDTO
    ) {

        return ResponseEntity.ok(
                evaluacionService.create(evaluacionDTO)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evaluacion> update(
            @PathVariable Integer id,
            @RequestBody Evaluacion evaluacion
    ) {

        return ResponseEntity.ok(
                evaluacionService.update(id, evaluacion)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {

        evaluacionService.delete(id);

        return ResponseEntity.noContent().build();
    }
}