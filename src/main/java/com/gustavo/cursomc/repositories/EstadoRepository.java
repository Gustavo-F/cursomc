package com.gustavo.cursomc.repositories;

import java.util.List;

import com.gustavo.cursomc.domain.Estado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    
    @Transactional(readOnly = true)
    public List<Estado> findAllByOrderByNome();
}
