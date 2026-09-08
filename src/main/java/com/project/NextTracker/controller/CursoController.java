package com.project.NextTracker.controller;

import com.project.NextTracker.dto.CursoDetalleDTO;
import com.project.NextTracker.model.Curso;
import com.project.NextTracker.service.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://nexttracker-frontend.onrender.com"
})
@RestController
@RequestMapping("malla/cursos")
public class CursoController {
    private final CursoService cursoService;


    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> getAllCursos(){
        return ResponseEntity.ok(this.cursoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> getCursoById(@PathVariable Integer id){
        return ResponseEntity.ok(this.cursoService.findById(id));
    }

    @GetMapping("/ciclo/{ciclo}")
    public ResponseEntity<List<Curso>> getCursoByCiclo(@PathVariable String ciclo){
        return ResponseEntity.ok(this.cursoService.findByCiclo(ciclo));
    }

    @PutMapping("/updateNota/{id}/{nuevaNota}")
    public ResponseEntity<Curso> updateNotaCurso(@PathVariable Integer id, @PathVariable BigDecimal nuevaNota){
        return ResponseEntity.ok(this.cursoService.updateNota(id,nuevaNota));
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<CursoDetalleDTO> getDetalle(@PathVariable Integer id) {
        return ResponseEntity.ok(this.cursoService.getDetalle(id));
    }

    @PutMapping("/matricular/{id}")
    public ResponseEntity<Curso> matricularCurso(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                this.cursoService.matricularCurso(id)
        );
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<Curso> finalizarCurso(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(
                cursoService.finalizarCurso(id)
        );
    }
}
