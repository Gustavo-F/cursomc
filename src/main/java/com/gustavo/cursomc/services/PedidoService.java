package com.gustavo.cursomc.services;

import java.util.Optional;

import com.gustavo.cursomc.domain.Pedido;
import com.gustavo.cursomc.repositories.PedidoRepository;
import com.gustavo.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository repo;

    public Pedido buscar(Integer id) {
        Optional<Pedido> pedidoObj = repo.findById(id);
        
        return pedidoObj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()
        ));
    }
}
