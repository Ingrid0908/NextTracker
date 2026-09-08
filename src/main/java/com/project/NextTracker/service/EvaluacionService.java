package com.project.NextTracker.service;

import com.project.NextTracker.model.Curso;
import com.project.NextTracker.model.Evaluacion;
import com.project.NextTracker.repository.CursoRepository;
import com.project.NextTracker.repository.EvaluacionRepository;
import org.springframework.stereotype.Service;
import com.project.NextTracker.dto.EvaluacionDTO;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final CursoRepository cursoRepository;

    public EvaluacionService(
            EvaluacionRepository evaluacionRepository,
            CursoRepository cursoRepository
    ) {
        this.evaluacionRepository = evaluacionRepository;
        this.cursoRepository = cursoRepository;
    }

    public List<Evaluacion> findByCursoId(Integer idCurso) {

        return evaluacionRepository.findByCursoId(idCurso);

    }

    public Evaluacion create(EvaluacionDTO dto) {

        if (dto.getIdCurso() == null) {
            throw new IllegalArgumentException(
                    "Debe indicar el curso de la evaluación"
            );
        }

        Curso curso = cursoRepository.findById(dto.getIdCurso())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Curso no encontrado con el id: "
                                        + dto.getIdCurso()
                        )
                );

        validarPorcentaje(
                dto.getPorcentaje(),
                dto.getPorcentajeObtenido()
        );

        BigDecimal porcentajeActual =
                evaluacionRepository
                        .findByCursoId(dto.getIdCurso())
                        .stream()
                        .map(Evaluacion::getPorcentaje)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        BigDecimal nuevoTotal =
                porcentajeActual.add(
                        dto.getPorcentaje()
                );

        if (nuevoTotal.compareTo(BigDecimal.valueOf(100)) > 0) {

            throw new IllegalArgumentException(
                    "El porcentaje total de las evaluaciones no puede superar el 100%"
            );
        }

        Evaluacion evaluacion = new Evaluacion();

        evaluacion.setCurso(curso);
        evaluacion.setNombre(dto.getNombre());
        evaluacion.setPorcentaje(dto.getPorcentaje());
        evaluacion.setPorcentajeObtenido(
                dto.getPorcentajeObtenido()
        );

        return evaluacionRepository.save(evaluacion);
    }

    public Evaluacion update(
            Integer id,
            Evaluacion evaluacion
    ) {

        Evaluacion existing =
                evaluacionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Evaluación no encontrada"
                                )
                        );

        validarPorcentaje(
                evaluacion.getPorcentaje(),
                evaluacion.getPorcentajeObtenido()
        );

        BigDecimal porcentajeOtros =
                evaluacionRepository
                        .findByCursoId(existing.getCurso().getId())
                        .stream()
                        .filter(e -> !e.getId().equals(id))
                        .map(Evaluacion::getPorcentaje)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        BigDecimal nuevoTotal =
                porcentajeOtros.add(
                        evaluacion.getPorcentaje()
                );

        if (nuevoTotal.compareTo(BigDecimal.valueOf(100)) > 0) {

            throw new IllegalArgumentException(
                    "El porcentaje total de las evaluaciones no puede superar el 100%"
            );
        }

        existing.setNombre(
                evaluacion.getNombre()
        );

        existing.setPorcentaje(
                evaluacion.getPorcentaje()
        );

        existing.setPorcentajeObtenido(
                evaluacion.getPorcentajeObtenido()
        );

        return evaluacionRepository.save(existing);
    }

    public void delete(Integer id) {

        Evaluacion evaluacion =
                evaluacionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Evaluación no encontrada"
                                )
                        );

        evaluacionRepository.delete(evaluacion);
    }

    private void validarPorcentaje(
            BigDecimal porcentaje,
            BigDecimal porcentajeObtenido
    ) {

        if (porcentaje == null ||
                porcentaje.compareTo(BigDecimal.ZERO) < 0 ||
                porcentaje.compareTo(BigDecimal.valueOf(100)) > 0) {

            throw new IllegalArgumentException(
                    "El porcentaje debe estar entre 0 y 100"
            );
        }

        if (porcentajeObtenido == null ||
                porcentajeObtenido.compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "El porcentaje obtenido no puede ser negativo"
            );
        }

        if (porcentajeObtenido.compareTo(porcentaje) > 0) {

            throw new IllegalArgumentException(
                    "El porcentaje obtenido no puede ser mayor al porcentaje de la evaluación"
            );
        }
    }

    @Transactional
    public Evaluacion actualizarPorcentajeObtenido(
            Integer id,
            BigDecimal porcentajeObtenido
    ) {

        Evaluacion evaluacion = evaluacionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Evaluación no encontrada con el id: " + id
                        )
                );

        validarPorcentaje(
                evaluacion.getPorcentaje(),
                porcentajeObtenido
        );

        evaluacion.setPorcentajeObtenido(porcentajeObtenido);

        return evaluacionRepository.save(evaluacion);
    }
}