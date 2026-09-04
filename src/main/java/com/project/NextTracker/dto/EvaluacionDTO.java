package com.project.NextTracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EvaluacionDTO {

    private Integer idCurso;

    private String nombre;

    private BigDecimal porcentaje;

    private BigDecimal porcentajeObtenido;
}