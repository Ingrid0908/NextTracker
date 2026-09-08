package com.project.NextTracker.service;

import com.project.NextTracker.dto.CursoDetalleDTO;
import com.project.NextTracker.model.Curso;
import com.project.NextTracker.model.EstadoCurso;
import com.project.NextTracker.model.Evaluacion;
import com.project.NextTracker.repository.CursoRepository;
import com.project.NextTracker.repository.EvaluacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CursoService {

    private static final BigDecimal NOTA_MINIMA_APROBACION =
            BigDecimal.valueOf(67.5);

    private final CursoRepository cursoRepository;
    private final EvaluacionRepository evaluacionRepository;

    public CursoService(
            CursoRepository cursoRepository,
            EvaluacionRepository evaluacionRepository
    ) {
        this.cursoRepository = cursoRepository;
        this.evaluacionRepository = evaluacionRepository;
    }

    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    public Curso findById(Integer id) {
        return cursoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Curso no encontrado con el id: " + id
                        )
                );
    }

    public List<Curso> findByCiclo(String ciclo) {
        return cursoRepository.findByCiclo(ciclo);
    }

    @Transactional
    public Curso matricularCurso(Integer id) {

        Curso curso = findById(id);

        if (curso.getEstado() != EstadoCurso.pendiente && curso.getEstado() != EstadoCurso.reprobado) {
            throw new IllegalStateException(
                    "El curso no puede ser matriculado nuevamente"
            );
        }

        if (curso.getEstado() == EstadoCurso.reprobado) {

            evaluacionRepository.deleteAll(
                    evaluacionRepository.findByCursoId(id)
            );
        }

        curso.setEstado(EstadoCurso.matriculado);
        curso.setNota(null);

        return cursoRepository.save(curso);
    }

    @Transactional
    public Curso finalizarCurso(Integer id) {

        Curso curso = findById(id);

        if (curso.getEstado() != EstadoCurso.matriculado) {
            throw new IllegalStateException(
                    "El curso debe estar matriculado para poder finalizarlo"
            );
        }

        List<Evaluacion> evaluaciones =
                evaluacionRepository.findByCursoId(id);

        if (evaluaciones.isEmpty()) {
            throw new IllegalStateException(
                    "No se puede finalizar un curso sin evaluaciones"
            );
        }

        BigDecimal notaFinal =
                evaluaciones
                        .stream()
                        .map(Evaluacion::getPorcentajeObtenido)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        curso.setNota(notaFinal);

        if (notaFinal.compareTo(NOTA_MINIMA_APROBACION) >= 0) {
            curso.setEstado(EstadoCurso.aprobado);
        } else {
            curso.setEstado(EstadoCurso.reprobado);
        }

        return cursoRepository.save(curso);
    }

    @Transactional
    public Curso updateEstado(
            Integer id,
            EstadoCurso nuevoEstado
    ) {

        Curso curso = findById(id);

        curso.setEstado(nuevoEstado);

        return cursoRepository.save(curso);
    }

    @Transactional
    public Curso updateNota(
            Integer id,
            BigDecimal nuevaNota
    ) {

        Curso curso = findById(id);

        curso.setNota(nuevaNota);

        EstadoCurso nuevoEstado =
                nuevaNota.compareTo(NOTA_MINIMA_APROBACION) >= 0
                        ? EstadoCurso.aprobado
                        : EstadoCurso.reprobado;

        curso.setEstado(nuevoEstado);

        return cursoRepository.save(curso);
    }

    public CursoDetalleDTO getDetalle(Integer id) {

        Curso curso = findById(id);

        return new CursoDetalleDTO(
                curso.getNombre(),
                curso.getNota(),
                curso.getEstado()
        );
    }
}