package com.gustavo.cursomc.repositories;

import com.gustavo.cursomc.domain.ItemPedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
    
}
