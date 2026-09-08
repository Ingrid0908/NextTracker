package com.project.NextTracker.controller;

import com.project.NextTracker.model.Requisito;
import com.project.NextTracker.service.RequisitoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://nexttracker-frontend.onrender.com"
})
@RestController
@RequestMapping("malla/requisitos")
public class RequisitoController {

    private final RequisitoService requisitoService;

    public RequisitoController(RequisitoService requisitoService) {
        this.requisitoService = requisitoService;
    }

    @GetMapping("/prerequisitos/{idCurso}")
    public ResponseEntity<List<Requisito>> getRequisitoByCursoId(@PathVariable Integer idCurso){
        return ResponseEntity.ok(this.requisitoService.getRequisitosByCurso(idCurso));
    }

    @GetMapping("/correquisitos/{idCurso}")
    public ResponseEntity<List<Requisito>> getCorequisitoByCursoId(@PathVariable Integer idCurso){
        return ResponseEntity.ok(this.requisitoService.getCorrequisitosByCurso(idCurso));
    }
}
