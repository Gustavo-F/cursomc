package com.gustavo.cursomc.repositories;

import com.gustavo.cursomc.domain.Estado;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    
}
