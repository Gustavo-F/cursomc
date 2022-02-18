package com.gustavo.cursomc.services;

import java.util.Optional;

import com.gustavo.cursomc.domain.Cliente;
import com.gustavo.cursomc.repositories.ClienteRepository;
import com.gustavo.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository repo;

    public Cliente buscar(Integer id) {
		Optional<Cliente> clienteObj = repo.findById(id);
		
		return clienteObj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()
		));
	}
}
