package com.project.NextTracker.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Entity
@Table(name = "curso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 20)
    private String sigla;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, length = 15)
    private String ciclo;

    private Integer anio;

    @Column(nullable = false)
    private int horas = 0;

    @Column(nullable = false)
    private int creditos = 0;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "estado_curso")
    private EstadoCurso estado = EstadoCurso.pendiente;

    @Column(precision = 3, scale = 1)
    private BigDecimal nota;
}