package com.project.NextTracker.dto;

import com.project.NextTracker.model.EstadoCurso;

import java.math.BigDecimal;

public record CursoDetalleDTO(
        String nombre,
        BigDecimal nota,
        EstadoCurso estado
) {}