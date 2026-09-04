package com.project.NextTracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "requisito")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Requisito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "tipo_requisito")
    private TipoRequisito tipo;

    @Column(nullable = false)
    private Short grupoNum = 1;

    @Column(nullable = false, length = 20)
    private String requisitoSigla;

    @ManyToOne
    @JoinColumn(name = "requisito_curso_id")
    private Curso requisitoCurso;
}