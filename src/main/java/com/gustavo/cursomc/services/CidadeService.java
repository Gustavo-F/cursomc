package com.gustavo.cursomc.services;

import java.util.List;

import com.gustavo.cursomc.domain.Cidade;
import com.gustavo.cursomc.repositories.CidadeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {
    
    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> findByEstado(Integer estadoId) {
        return cidadeRepository.findCidades(estadoId);
    }
}
