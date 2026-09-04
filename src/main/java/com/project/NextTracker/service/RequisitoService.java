package com.project.NextTracker.service;

import com.project.NextTracker.model.Requisito;
import com.project.NextTracker.repository.RequisitoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequisitoService {

    private final RequisitoRepository requisitoRepository;

    public RequisitoService(RequisitoRepository requisitoRepository) {
        this.requisitoRepository = requisitoRepository;
    }

    public List<Requisito> getRequisitosByCurso(Integer idCurso){
        return requisitoRepository.findRequisitoByCursoId(idCurso);
    }

    public List<Requisito> getCorrequisitosByCurso(Integer idCurso){
        return requisitoRepository.findCorrequisitoByCursoId(idCurso);
    }
}
