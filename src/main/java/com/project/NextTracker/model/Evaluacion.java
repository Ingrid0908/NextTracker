package com.project.NextTracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "evaluacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal porcentaje;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal porcentajeObtenido;
}