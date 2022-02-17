package com.gustavo.cursomc.repositories;

import com.gustavo.cursomc.domain.Cidade;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    
}
