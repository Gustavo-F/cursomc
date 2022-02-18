package com.gustavo.cursomc.repositories;

import com.gustavo.cursomc.domain.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    
}
