package com.gustavo.cursomc.services;

import java.util.List;

import com.gustavo.cursomc.domain.Estado;
import com.gustavo.cursomc.repositories.EstadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> findAll() {
        return estadoRepository.findAllByOrderByNome();
    }
}
