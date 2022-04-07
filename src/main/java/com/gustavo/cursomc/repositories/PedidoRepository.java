package com.gustavo.cursomc.repositories;

import com.gustavo.cursomc.domain.Cliente;
import com.gustavo.cursomc.domain.Pedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Transactional(readOnly = true)
    public Page<Pedido> findByCliente(Cliente cliente, Pageable pageable);
}
