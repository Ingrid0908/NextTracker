package com.project.NextTracker.repository;

import com.project.NextTracker.model.Requisito;
import com.project.NextTracker.model.TipoRequisito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequisitoRepository extends JpaRepository<Requisito,Integer> {
    List<Requisito> findByCursoIdAndTipo(Integer cursoId, TipoRequisito tipo);

    default List<Requisito> findRequisitoByCursoId(Integer cursoId){
        return findByCursoIdAndTipo(cursoId, TipoRequisito.prerequisito);
    }

    default List<Requisito> findCorrequisitoByCursoId(Integer cursoId){
        return findByCursoIdAndTipo(cursoId, TipoRequisito.correquisito);
    }
}
