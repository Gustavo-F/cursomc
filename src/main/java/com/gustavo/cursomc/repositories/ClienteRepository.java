package com.gustavo.cursomc.repositories;

import com.gustavo.cursomc.domain.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
}
