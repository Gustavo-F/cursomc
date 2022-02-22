package com.gustavo.cursomc.services;

import java.util.List;
import java.util.Optional;

import com.gustavo.cursomc.domain.Cliente;
import com.gustavo.cursomc.dto.ClienteDTO;
import com.gustavo.cursomc.repositories.ClienteRepository;
import com.gustavo.cursomc.services.exceptions.DataIntegrityException;
import com.gustavo.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository repo;

	public List<Cliente> findAll() {
		return repo.findAll();
	}

    public Cliente find(Integer id) {
		Optional<Cliente> clienteObj = repo.findById(id);
		
		return clienteObj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()
		));
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
    	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    	return repo.findAll(pageRequest);
    }

	public Cliente update(Cliente cliente) {
        Cliente newCliente = find(cliente.getId());
		updateData(newCliente, cliente);
		
		return repo.save(newCliente);
    }

    public void delete(Integer id) {
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas.");
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
		}
    }
    
    public Cliente fromDTO(ClienteDTO clienteDTO) {
    	return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
    }

	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}
}
