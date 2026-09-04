package com.project.NextTracker.repository;

import com.project.NextTracker.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion,Integer> {
    List<Evaluacion> findByCursoId(Integer cursoId);
}
